package com.uisrael.TurnoSmart.controlador;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.modelo.Estudiante;
import com.uisrael.TurnoSmart.modelo.Representante;
import com.uisrael.TurnoSmart.modelo.Usuario;
import com.uisrael.TurnoSmart.repositorio.UsuarioRepositorio;
import com.uisrael.TurnoSmart.servicio.CitaServicio;
import com.uisrael.TurnoSmart.servicio.EstudianteServicio;

@Controller
@RequestMapping("/docente")
public class DocenteControlador {

	private final UsuarioRepositorio usuarioRepositorio;
	private final CitaServicio citaServicio;
	private final EstudianteServicio estudianteServicio;

	public DocenteControlador(UsuarioRepositorio usuarioRepositorio, CitaServicio citaServicio,
			EstudianteServicio estudianteServicio) {
		this.usuarioRepositorio = usuarioRepositorio;
		this.citaServicio = citaServicio;
		this.estudianteServicio = estudianteServicio;
	}

	@GetMapping("/PrincipalDocente")
	public String mostrarPaginaDocente() {
		return "PrincipalDocente";
	}

	@GetMapping("/Perfil")
	public String mostrarPerfilDocente() {
		return "PerfilDocente";
	}

	@GetMapping("/citas")
	public String mostrarPaginaCitas(Model model, Principal principal) {
		// Obtener el ID del docente desde el usuario autenticado
		String username = principal.getName();
		Usuario usuario = usuarioRepositorio.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Docente docente = usuario.getDocente();
		if (docente == null) {
			throw new RuntimeException("El usuario no tiene un docente asociado");
		}

		// Obtener los estudiantes del docente
		List<Estudiante> estudiantes = estudianteServicio.obtenerEstudiantesPorDocente(docente.getIdDocente());

		// Pasar los estudiantes al modelo
		model.addAttribute("estudiantes", estudiantes);
		return "CitasDocentes";
	}

	@PostMapping("/citas/agendar")
	public String agendarCitaPorDocente(@ModelAttribute Cita cita, @RequestParam("idEstudiante") Integer idEstudiante,
			Principal principal) {

		// Obtener el ID del docente basado en el usuario autenticado
		String username = principal.getName();
		Usuario usuario = usuarioRepositorio.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Docente docente = usuario.getDocente();
		if (docente == null) {
			throw new RuntimeException("El usuario no tiene un docente asociado");
		}

		// Llamar al servicio para agendar la cita
		citaServicio.agendarCitaPorDocente(cita, idEstudiante, docente.getIdDocente());

		// Redirigir a la página de éxito o listado
		return "redirect:/docente/citas/agendadas";
	}

	@GetMapping("/citas/agendadas")
	public String mostrarCitasAgendadas(Model model, Principal principal) {
	    // Obtener el docente autenticado
	    String username = principal.getName();
	    Docente docente = usuarioRepositorio.findByUsername(username)
	                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
	                        .getDocente();

	    // Obtener las citas del docente
	    List<Cita> citas = citaServicio.obtenerCitasPorDocente(docente.getIdDocente());

	    // Clasificar las citas por tipo
	    List<Cita> citasConEstudiante = citas.stream()
	        .filter(cita -> cita.getEstudiante() != null)
	        .toList();

	    List<Cita> citasSinEstudiante = citas.stream()
	        .filter(cita -> cita.getEstudiante() == null)
	        .toList();

	    // Agregar la lista de estudiantes relacionados a cada representante en las citas sin estudiante
	    for (Cita cita : citasSinEstudiante) {
	        Representante representante = cita.getRepresentante();
	        if (representante != null) {
	            // Cargar los estudiantes relacionados al representante
	            representante.setEstudiantes(
	                estudianteServicio.obtenerEstudiantesPorRepresentante(representante.getIdRepresentante())
	            );
	        }
	    }

	    // Pasar las citas al modelo
	    model.addAttribute("citasConEstudiante", citasConEstudiante);
	    model.addAttribute("citasSinEstudiante", citasSinEstudiante);

	    return "CitasAgendadasDocente";
	}


}
