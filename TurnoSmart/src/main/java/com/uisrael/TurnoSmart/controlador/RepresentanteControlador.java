package com.uisrael.TurnoSmart.controlador;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uisrael.TurnoSmart.modelo.Cita;
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
	public String mostrarPaginaRepresentante() {
		return "PrincipalRepresentante";
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

		return "CitasRepresentantes";
	}

	@PostMapping("/agendar-cita")
	public String agendarCita(@RequestParam("idDocente") Integer idDocente,
			@RequestParam("idHorario") Integer idHorario, @RequestParam("fecha") LocalDate fecha, Principal principal,
			Model model) {
		try {
			// Validar que la fecha seleccionada coincida con el día del horario
			HorarioDisponible horario = horarioServicio.obtenerPorId(idHorario);
			if (!validarFechaHorario(fecha, horario)) {
				throw new IllegalArgumentException("La fecha seleccionada no coincide con el día del horario.");
			}

			// Obtener el representante autenticado
			String username = principal.getName();
			Representante representante = representanteServicio.obtenerPorUsuario(username);

			// Llamar al servicio para agendar la cita
			citaServicio.agendarCitaPorRepresentante(idDocente, idHorario, representante, fecha);

			// Obtener el docente para enviar el correo
			Docente docente = docenteRepositorio.findById(idDocente)
					.orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + idDocente));

			// Crear los datos del correo
			String destinatario = docente.getEmail(); // Correo del docente
			String asunto = "Nueva cita agendada CEIAF";
			String mensaje = "Estimado/a liceniado/a " + docente.getNombre() + " " + docente.getApellido() + ",\n\n"
					+ "El representante " + representante.getNombre() + " " + representante.getApellido()
					+ " ha agendado una nueva cita para la fecha:\n" + "Fecha: " + fecha + "\n" + "Hora: "
					+ horario.getHoraInicio() + "\n\n" + "Por favor, revise el sistema para más detalles y espera su confirmación.\n\n"
					+ "Atentamente,\n" + "Colegio Antonio Flores";

			// Enviar el correo
			emailServicio.enviarCorreo(destinatario, asunto, mensaje);

			// Mensaje de éxito al modelo
			model.addAttribute("success", "La cita fue agendada correctamente y se notificó al docente por correo.");

			// Retornar directamente a la vista
			return "CitasRepresentantes";
		} catch (IllegalArgumentException e) {
			// Agregar mensaje de error al modelo y redirigir al formulario
			model.addAttribute("error", e.getMessage());
			return "CitasRepresentantes";
		} catch (Exception e) {
			// Manejar cualquier otro error
			model.addAttribute("error", "Error al agendar la cita: " + e.getMessage());
			return "CitasRepresentantes";
		}
	}

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

}
