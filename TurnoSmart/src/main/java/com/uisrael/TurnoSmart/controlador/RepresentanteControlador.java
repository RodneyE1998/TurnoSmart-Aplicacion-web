package com.uisrael.TurnoSmart.controlador;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
import com.uisrael.TurnoSmart.modelo.HorarioDisponible;
import com.uisrael.TurnoSmart.modelo.Representante;
import com.uisrael.TurnoSmart.servicio.CitaServicio;
import com.uisrael.TurnoSmart.servicio.DocenteServicio;
import com.uisrael.TurnoSmart.servicio.HorarioDisponibleServicio;
import com.uisrael.TurnoSmart.servicio.RepresentanteServicio;

@Controller
@RequestMapping("/representante")
public class RepresentanteControlador {

	private final HorarioDisponibleServicio horarioServicio;
	private final DocenteServicio docenteServicio;
	private final CitaServicio citaServicio;
	private final RepresentanteServicio representanteServicio;

	public RepresentanteControlador(HorarioDisponibleServicio horarioServicio, DocenteServicio docenteServicio,
			CitaServicio citaServicio, RepresentanteServicio representanteServicio) {
		this.horarioServicio = horarioServicio;
		this.docenteServicio = docenteServicio;
		this.citaServicio = citaServicio;
		this.representanteServicio = representanteServicio;
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
	public String mostrarPerfilRepresentante() {
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

			// Mensaje de éxito al modelo
			model.addAttribute("success", "La cita fue agendada correctamente.");

			// Retornar directamente a la vista
			return "CitasRepresentantes";
		} catch (IllegalArgumentException e) {
			// Agregar mensaje de error al modelo y redirigir al formulario
			model.addAttribute("error", e.getMessage());
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




}
