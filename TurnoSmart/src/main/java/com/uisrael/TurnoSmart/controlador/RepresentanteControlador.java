package com.uisrael.TurnoSmart.controlador;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uisrael.TurnoSmart.dto.CitaDTO;
import com.uisrael.TurnoSmart.dto.DocenteDTO;
import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Cita.TipoCita;
import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.modelo.Estudiante;
import com.uisrael.TurnoSmart.modelo.HorarioDisponible;
import com.uisrael.TurnoSmart.modelo.Representante;
import com.uisrael.TurnoSmart.modelo.Usuario;
import com.uisrael.TurnoSmart.repositorio.DocenteRepositorio;
import com.uisrael.TurnoSmart.repositorio.UsuarioRepositorio;
import com.uisrael.TurnoSmart.servicio.CitaServicio;
import com.uisrael.TurnoSmart.servicio.DocenteServicio;
import com.uisrael.TurnoSmart.servicio.EmailServicio;
import com.uisrael.TurnoSmart.servicio.HorarioDisponibleServicio;
import com.uisrael.TurnoSmart.servicio.RepresentanteServicio;

@Controller
@RequestMapping("/representante")
public class RepresentanteControlador {

	private final HorarioDisponibleServicio horarioServicio;
	private final DocenteServicio docenteServicio;
	private final CitaServicio citaServicio;
	private final RepresentanteServicio representanteServicio;
	private final EmailServicio emailServicio;
	private final DocenteRepositorio docenteRepositorio;
	private final UsuarioRepositorio usuarioRepositorio;

	public RepresentanteControlador(HorarioDisponibleServicio horarioServicio, DocenteServicio docenteServicio,
			CitaServicio citaServicio, RepresentanteServicio representanteServicio, EmailServicio emailServicio,
			DocenteRepositorio docenteRepositorio, UsuarioRepositorio usuarioRepositorio) {
		this.horarioServicio = horarioServicio;
		this.docenteServicio = docenteServicio;
		this.citaServicio = citaServicio;
		this.representanteServicio = representanteServicio;
		this.emailServicio = emailServicio;
		this.docenteRepositorio = docenteRepositorio;
		this.usuarioRepositorio = usuarioRepositorio;
	}

	@GetMapping("/PrincipalRepresentante")
	public String mostrarPaginaRepresentante(Model model, Principal principal) {
	    // Obtener el usuario autenticado
	    String username = principal.getName();
	    Usuario usuario = usuarioRepositorio.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    // Obtener el representante asociado al usuario
	    Representante representante = usuario.getRepresentante();
	    if (representante == null) {
	        throw new RuntimeException("El usuario no tiene un representante asociado.");
	    }

	    // Pasar los datos del representante al modelo
	    model.addAttribute("nombreRepresentante", representante.getNombre());
	    model.addAttribute("apellidoRepresentante", representante.getApellido());

	    return "PrincipalRepresentante"; // Retorna la vista
	}


	@GetMapping("/DocentesBasica")
	public String mostrarDocentesBasica() {
		return "DocenteBasica";
	}

	@GetMapping("/DocentesBachillerato")
	public String mostrarDocentesBachillerato() {
		return "DocenteBachillerato";
	}

	@GetMapping("/Perfil-Representante")
	public String mostrarPerfilRepresentante(Principal principal, Model model) {
		// Obtener el usuario autenticado
		String username = principal.getName();
		Usuario usuario = usuarioRepositorio.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		// Obtener el representante asociado al usuario
		Representante representante = usuario.getRepresentante();
		if (representante == null) {
			throw new RuntimeException("El usuario no tiene un representante asociado.");
		}

		// Obtener los estudiantes asociados al representante
		List<Estudiante> estudiantes = representante.getEstudiantes();

		// Pasar los datos al modelo
		model.addAttribute("representante", representante);
		model.addAttribute("estudiantes", estudiantes);

		return "PerfilRepresentante";
	}

	// AGENDAR CITAS CON LOS DOCENTES 
	@GetMapping("/Citas")
	public String mostrarFormularioAgendarCita(Model model) {
		// Cargar la lista de docentes
		List<Docente> docentes = docenteServicio.obtenerTodosLosDocentes();
		if (docentes.isEmpty()) {
			System.out.println("No se encontraron docentes.");
		}
		model.addAttribute("docentes", docentes);

		// Cargar los horarios disponibles
		List<HorarioDisponible> horarios = horarioServicio.obtenerTodosLosHorarios();
		if (horarios.isEmpty()) {
			System.out.println("No se encontraron horarios.");
		}
		model.addAttribute("horarios", horarios);
		
		 // Agregar los tipos de citas al modelo (Enum)
		model.addAttribute("tiposCita", Cita.TipoCita.values());


		return "CitasRepresentantes";
	}
	
	private ResponseEntity<String> procesarAgendamiento(Integer idDocente, Integer idHorario, LocalDate fecha, Integer representanteId, String motivoCita, TipoCita tipoCita) {
	    try {
	        // Obtener el horario seleccionado
	        HorarioDisponible horario = horarioServicio.obtenerPorId(idHorario);
	        if (!validarFechaHorario(fecha, horario)) {
	            throw new IllegalArgumentException("La fecha seleccionada no coincide con el día del horario.");
	        }

	        // Obtener el representante
	        Representante representante = representanteServicio.obtenerPorId(representanteId);

	        // Agendar la cita
	        citaServicio.agendarCitaPorRepresentante(idDocente, idHorario, representante, fecha, motivoCita, tipoCita);

	        // Buscar la cita recién creada
	        Cita cita = citaServicio.obtenerCitaPorRepresentanteYFecha(representante.getIdRepresentante(), fecha, horario.getHoraInicio());
	        if (cita == null) {
	            throw new RuntimeException("Error al recuperar la cita agendada.");
	        }

	        // Obtener el docente y su correo
	        Docente docente = docenteRepositorio.findById(idDocente)
	                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + idDocente));

	        // Enviar correo al docente
	        emailServicio.enviarCorreoConfirmacionCitaDocente(
	            docente.getEmail(),
	            docente.getNombre() + " " + docente.getApellido(),
	            representante.getNombre(),
	            fecha.toString(),
	            horario.getHoraInicio().toString(),
	            cita.getIdCita(), 
	            cita.getTipoCita().toString(), 
	            cita.getMotivoCita() 
	        );

	        return ResponseEntity.ok("Cita agendada correctamente y notificada al docente por correo.");

	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("Error al agendar la cita: " + e.getMessage());
	    }
	}


	@PostMapping("/agendar-cita")
	public String agendarCitaWeb(@RequestParam("idDocente") Integer idDocente,
	                             @RequestParam("idHorario") Integer idHorario,
	                             @RequestParam("fecha") LocalDate fecha,
	                             @RequestParam("motivoCita") String motivoCita,
	                             @RequestParam("tipoCita") String tipoCitaStr, // Recibimos como String
	                             Principal principal, Model model) {

	    try {
	        // Validar que tipoCitaStr no sea nulo o vacío
	        if (tipoCitaStr == null || tipoCitaStr.isEmpty()) {
	            model.addAttribute("error", "El tipo de cita no puede estar vacío.");
	            return "CitasRepresentantes";
	        }

	        // Convertir String a Enum (haciendo referencia a Cita.TipoCita)
	        Cita.TipoCita tipoCita;
	        try {
	            tipoCita = Cita.TipoCita.valueOf(tipoCitaStr.trim().toUpperCase());
	        } catch (IllegalArgumentException e) {
	            model.addAttribute("error", "Error: Tipo de cita no válido. Debe ser 'ACADEMICO' o 'DISCIPLINARIO'.");
	            return "CitasRepresentantes";
	        }

	        // Obtener el representante autenticado
	        String username = principal.getName();
	        Representante representante = representanteServicio.obtenerPorUsuario(username);

	        // Llamar al método común de procesamiento con los nuevos datos
	        ResponseEntity<String> resultado = procesarAgendamiento(
	                idDocente, idHorario, fecha, representante.getIdRepresentante(), motivoCita, tipoCita);

	        // Manejar la respuesta y devolver la vista correcta
	        if (resultado.getStatusCode().is2xxSuccessful()) {
	            model.addAttribute("success", resultado.getBody());
	        } else {
	            model.addAttribute("error", resultado.getBody());
	        }

	    } catch (Exception e) {
	        model.addAttribute("error", "Error al procesar la cita: " + e.getMessage());
	    }

	    return "CitasRepresentantes";
	}


	
	@PostMapping("/api/agendar-cita")
	@ResponseBody
	public ResponseEntity<String> agendarCita(@RequestBody CitaRequest citaRequest) {
	    return procesarAgendamiento(
	        citaRequest.getIdDocente(),
	        citaRequest.getIdHorario(),
	        citaRequest.getFecha(),
	        citaRequest.getRepresentanteId(), 
	        citaRequest.getMotivoCita(), 
	        citaRequest.getTipoCita() 
	    );
	}
	
	public static class CitaRequest {
	    private Integer idDocente;
	    private Integer idHorario;
	    private LocalDate fecha;
	    private Integer representanteId;
	    private String motivoCita;  
	    private TipoCita tipoCita;  

	    // Getters y Setters
	    public Integer getIdDocente() { return idDocente; }
	    public void setIdDocente(Integer idDocente) { this.idDocente = idDocente; }
	    public Integer getIdHorario() { return idHorario; }
	    public void setIdHorario(Integer idHorario) { this.idHorario = idHorario; }
	    public LocalDate getFecha() { return fecha; }
	    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
	    public Integer getRepresentanteId() { return representanteId; }
	    public void setRepresentanteId(Integer representanteId) { this.representanteId = representanteId; }
	    public String getMotivoCita() { return motivoCita; }
	    public void setMotivoCita(String motivoCita) { this.motivoCita = motivoCita; }
	    public TipoCita getTipoCita() { return tipoCita; }
	    public void setTipoCita(TipoCita tipoCita) { this.tipoCita = tipoCita; }
	}

	
	/*Obtener los horarios por Docentes*/
	@GetMapping("/horarios/por-docente")
	@ResponseBody
	public List<HorarioDisponible> obtenerHorariosPorDocente(@RequestParam("idDocente") Integer idDocente) {
		return horarioServicio.obtenerHorariosPorDocente(idDocente);
	}

	private boolean validarFechaHorario(LocalDate fecha, HorarioDisponible horario) {
		// Convertir el día de la fecha seleccionada al nombre del día en español
		DayOfWeek diaSemana = fecha.getDayOfWeek();
		String diaSeleccionado = convertirDiaSemana(diaSemana);

		// Comparar con el día del horario
		return diaSeleccionado.equalsIgnoreCase(horario.getDia());
	}

	// Método auxiliar para convertir DayOfWeek a cadena
	private String convertirDiaSemana(DayOfWeek diaSemana) {
		switch (diaSemana) {
		case MONDAY:
			return "Lunes";
		case TUESDAY:
			return "Martes";
		case WEDNESDAY:
			return "Miércoles";
		case THURSDAY:
			return "Jueves";
		case FRIDAY:
			return "Viernes";
		case SATURDAY:
			return "Sábado";
		case SUNDAY:
			return "Domingo";
		default:
			throw new IllegalArgumentException("Día inválido: " + diaSemana);
		}
	}

	//Citas Agendadas WEB 
	@GetMapping("/citas-agendadas")
	public String listarCitasRepresentante(Principal principal, Model model) {
		// Obtener el representante autenticado
		String username = principal.getName();
		Representante representante = representanteServicio.obtenerPorUsuario(username);

		// Separar las citas en dos listas
		List<Cita> citasPorRepresentante = citaServicio.obtenerCitasPorRepresentante(representante.getIdRepresentante())
				.stream().filter(cita -> cita.getEstudiante() == null).collect(Collectors.toList());

		List<Cita> citasPorDocente = citaServicio.obtenerCitasPorRepresentante(representante.getIdRepresentante())
				.stream().filter(cita -> cita.getEstudiante() != null).collect(Collectors.toList());

		// Pasar las citas al modelo
		model.addAttribute("citasPorRepresentante", citasPorRepresentante);
		model.addAttribute("citasPorDocente", citasPorDocente);

		return "CitasAgendadasRepresentante";
	}
	
	//CitasAgendadasMovil 
	@GetMapping("/api/citas-agendadas")
	@ResponseBody
	public List<CitaDTO> obtenerCitasAgendadas(Principal principal) {
	    // Obtener el representante autenticado
	    String username = principal.getName();
	    Representante representante = representanteServicio.obtenerPorUsuario(username);

	    // Obtener citas
	    List<Cita> citas = citaServicio.obtenerCitasPorRepresentante(representante.getIdRepresentante());

	    // Convertir Cita a CitaDTO para solo enviar los datos necesarios
	    return citas.stream().map(cita -> new CitaDTO(
	            cita.getIdCita(),
	            cita.getFechaCita().toString(),
	            cita.getHoraCita().toString(),
	            cita.getDocentes().stream().findFirst().map(Docente::getNombre).orElse("Desconocido"),
	            cita.getEstadoCita() 
	    )).collect(Collectors.toList());
	}



	
	@PostMapping("/cancelar-cita")
	@ResponseBody
	public String cancelarCita(@RequestParam("idCita") Integer idCita) {
		try {
			// Cambiar el estado de la cita
			citaServicio.cancelarCita(idCita);

			// Obtener la cita para enviar el correo
			Cita cita = citaServicio.obtenerCitaPorId(idCita);

			// Validar que existan docentes asociados
			if (cita.getDocentes() != null && !cita.getDocentes().isEmpty()) {
				String destinatario = cita.getDocentes().get(0).getEmail(); // Correo del docente
				String asunto = "Cancelación de cita CEIAF";
				String mensaje = "Estimado/a liceniado/a " + cita.getDocentes().get(0).getNombre() + " "
						+ cita.getDocentes().get(0).getApellido() + ",\n\n" + "El representante "
						+ cita.getRepresentante().getNombre() + " " + cita.getRepresentante().getApellido()
						+ " ha cancelado la cita programada para:\n" + "Fecha: " + cita.getFechaCita() + "\n" + "Hora: "
						+ cita.getHoraCita() + "\n\n" + "Atentamente,\n" + "Colegio Antonio Flores";

				// Enviar el correo
				emailServicio.enviarCorreo(destinatario, asunto, mensaje);
			}

			return "Cita cancelada y correo enviado correctamente.";
		} catch (Exception e) {
			return "Error al cancelar la cita: " + e.getMessage();
		}
	}

	
	@PostMapping("/confirmar-cita")
	@ResponseBody
	public String confirmarCita(@RequestParam("idCita") Integer idCita) {
		try {
			// Cambiar el estado de la cita
			citaServicio.confirmarCita(idCita);

			// Obtener la cita para enviar el correo
			Cita cita = citaServicio.obtenerCitaPorId(idCita);

			// Validar que existan docentes asociados
			if (cita.getDocentes() != null && !cita.getDocentes().isEmpty()) {
				String destinatario = cita.getDocentes().get(0).getEmail(); // Correo del docente
				String asunto = "Confirmación de cita CEIAF";
				String mensaje = "Estimado/a liceniado/a " + cita.getDocentes().get(0).getNombre() + " "
						+ cita.getDocentes().get(0).getApellido() + ",\n\n" + "El representante "
						+ cita.getRepresentante().getNombre() + " " + cita.getRepresentante().getApellido()
						+ " ha confirmado la cita programada para:\n" + "Fecha: " + cita.getFechaCita() + "\n"
						+ "Hora: " + cita.getHoraCita() + "\n\n" + "Atentamente,\n" + "Colegio Antonio Flores";

				// Enviar el correo
				emailServicio.enviarCorreo(destinatario, asunto, mensaje);
			}

			return "Cita confirmada y correo enviado correctamente.";
		} catch (Exception e) {
			return "Error al confirmar la cita: " + e.getMessage();
		}
	}

	
	@GetMapping("/docentes-por-estudiante")
	@ResponseBody
	public List<Docente> obtenerDocentesPorEstudiante(Principal principal) {
		String username = principal.getName();
		return representanteServicio.obtenerDocentesAsociados(username);
	}
	
	@GetMapping("/docentes/disponibles")
	@ResponseBody
	public List<DocenteDTO> obtenerDocentesDisponibles(@RequestParam("id_representante") Integer idRepresentante) {
	    return representanteServicio.obtenerDocentesPorRepresentante(idRepresentante);
	}



}
