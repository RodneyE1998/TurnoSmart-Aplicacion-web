package com.uisrael.TurnoSmart.controlador;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.modelo.Estudiante;
import com.uisrael.TurnoSmart.modelo.Representante;
import com.uisrael.TurnoSmart.modelo.Role;
import com.uisrael.TurnoSmart.modelo.Usuario;
import com.uisrael.TurnoSmart.repositorio.CitaRepositorio;
import com.uisrael.TurnoSmart.repositorio.DocenteRepositorio;
import com.uisrael.TurnoSmart.repositorio.EstudianteRepositorio;
import com.uisrael.TurnoSmart.repositorio.RepresentanteRepositorio;
import com.uisrael.TurnoSmart.repositorio.RoleRepositorio;
import com.uisrael.TurnoSmart.repositorio.UsuarioRepositorio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

	private final DocenteRepositorio docenteRepositorio;
	private final RepresentanteRepositorio representanteRepositorio;
	private final EstudianteRepositorio estudianteRepositorio;
	private final UsuarioRepositorio usuarioRepositorio;
	private final RoleRepositorio roleRepositorio;
	private final CitaRepositorio citaRepositorio;
	private final BCryptPasswordEncoder passwordEncoder;

	public AdminControlador(DocenteRepositorio docenteRepositorio, RepresentanteRepositorio representanteRepositorio,
			EstudianteRepositorio estudianteRepositorio, UsuarioRepositorio usuarioRepositorio,
			RoleRepositorio roleRepositorio, CitaRepositorio citaRepositorio, BCryptPasswordEncoder passwordEncoder) {
		this.docenteRepositorio = docenteRepositorio;
		this.representanteRepositorio = representanteRepositorio;
		this.estudianteRepositorio = estudianteRepositorio;
		this.usuarioRepositorio = usuarioRepositorio;
		this.roleRepositorio = roleRepositorio;
		this.citaRepositorio = citaRepositorio;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/principal")
	public String mostrarDashboardAdmin(Model model) {
		return "AdminPrincipal";
	}

	@GetMapping("/gestionar-usuarios")
	public String gestionarUsuarios(Model model) {
		List<Representante> representantes = representanteRepositorio.findAll();
		List<Docente> docentes = docenteRepositorio.findAll();

		model.addAttribute("representantes", representantes);
		model.addAttribute("docentes", docentes);
		return "GestionarUsuarios";
	}

	// ******************************************************************
	// CREAR DOCENTE + USUARIO AUTOMÁTICO
	// ******************************************************************
	@PostMapping("/crear-docente")
	public String crearDocente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email,
			@RequestParam String telefono, @RequestParam String numeroIdentificacion, @RequestParam String username,
			@RequestParam String password) {
		// Crear y guardar el docente
		Docente docente = new Docente();
		docente.setNombre(nombre);
		docente.setApellido(apellido);
		docente.setEmail(email);
		docente.setTelefono(telefono);
		docente.setNumeroIdentificacion(numeroIdentificacion);
		docenteRepositorio.save(docente);

		// Crear usuario asociado
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(passwordEncoder.encode(password));
		usuario.setDocente(docente);
		usuarioRepositorio.save(usuario);
		// Asegurar que la lista de roles no sea null
		if (usuario.getRoles() == null) {
			usuario.setRoles(new ArrayList<>(new HashSet<Role>()));
		}

		// Asignar rol DOCENTE
		Role rolDocente = roleRepositorio.findOptionalByNombre("DOCENTE")
				.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
		usuario.getRoles().add(rolDocente);
		usuarioRepositorio.save(usuario);

		return "redirect:/admin/gestionar-usuarios?success";
	}

	// ******************************************************************
	// CREAR REPRESENTANTE + USUARIO AUTOMÁTICO
	// ******************************************************************
	@PostMapping("/crear-representante")
	public String crearRepresentante(@RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String email, @RequestParam String telefono, @RequestParam String cedula,
			@RequestParam String username, @RequestParam String password) {
		// Crear y guardar el representante
		Representante representante = new Representante();
		representante.setNombre(nombre);
		representante.setApellido(apellido);
		representante.setEmail(email);
		representante.setTelefono(telefono);
		representante.setCedula(cedula);
		representante.setActivo(true);
		representanteRepositorio.save(representante);

		// Crear usuario asociado
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(passwordEncoder.encode(password));
		usuario.setRepresentante(representante);
		usuarioRepositorio.save(usuario);

		// Asegurar que la lista de roles no sea null
		if (usuario.getRoles() == null) {
			usuario.setRoles(new ArrayList<>(new HashSet<Role>()));
		}

		// Asignar rol REPRESENTANTE
		Role rolRepresentante = roleRepositorio.findOptionalByNombre("REPRESENTANTE")
				.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
		usuario.getRoles().add(rolRepresentante);
		usuarioRepositorio.save(usuario);

		// Asociar el usuario al representante y guardar la actualización
		representante.setIdUsuario(usuario);
		representanteRepositorio.save(representante);

		return "redirect:/admin/gestionar-usuarios?success";
	}

	// ******************************************************************
	// CREAR ESTUDIANTE Y ASIGNARLO A UN REPRESENTANTE Y DOCENTES
	// ******************************************************************
	@PostMapping("/crear-estudiante")
	public String crearEstudiante(@RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String cedula, @RequestParam String curso, @RequestParam Integer idRepresentante,
			@RequestParam List<Integer> docentesSeleccionados) {

		// Obtener representante
		Representante representante = representanteRepositorio.findById(idRepresentante)
				.orElseThrow(() -> new RuntimeException("Representante no encontrado"));

		// Crear y guardar el estudiante
		Estudiante estudiante = new Estudiante();
		estudiante.setNombre(nombre);
		estudiante.setApellido(apellido);
		estudiante.setCedula(cedula);
		estudiante.setCurso(curso);
		estudiante.setRepresentante(representante);

		// Obtener lista de docentes y asignarlos al estudiante
		List<Docente> docentes = docenteRepositorio.findAllById(docentesSeleccionados);
		estudiante.setDocentes(docentes);

		estudianteRepositorio.save(estudiante);

		return "redirect:/admin/gestionar-usuarios?success";
	}

	// ******************************************************************
	// HTML PARA ABRIR LA PAGINA DE GESTIONAR DATOS
	// ******************************************************************
	@GetMapping("/gestionar-datos")
	public String gestionarDatos(Model model) {
		// Obtener listas de Docentes, Representantes, Estudiantes y Citas
		List<Docente> docentes = docenteRepositorio.findByActivoTrue();
		List<Representante> representantes = representanteRepositorio.findByActivoTrue();
		List<Estudiante> estudiantes = estudianteRepositorio.findByActivoTrue();
		List<Cita> citas = citaRepositorio.findAll();

		// Enviar datos a la vista
		model.addAttribute("docentes", docentes);
		model.addAttribute("representantes", representantes);
		model.addAttribute("estudiantes", estudiantes);
		model.addAttribute("citas", citas);

		return "GestionarDatos";
	}

	// ******************************************************************
	// METODOS PARA EDITAR Y ELIMINAR LOS DOCENTES
	// ******************************************************************
	@PostMapping("/editar-docente/{id}")
	@ResponseBody
	public Map<String, Object> editarDocente(@PathVariable Integer id, @RequestBody Map<String, String> data) {
		Map<String, Object> response = new HashMap<>();
		try {
			Docente docente = docenteRepositorio.findById(id)
					.orElseThrow(() -> new RuntimeException("Docente no encontrado"));

			docente.setNombre(data.get("nombre"));
			docente.setApellido(data.get("apellido"));
			docente.setEmail(data.get("email"));
			docente.setTelefono(data.get("telefono"));

			docenteRepositorio.save(docente);
			response.put("success", true);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		}
		return response;
	}

	@PostMapping("/eliminar-docente/{id}")
	@ResponseBody
	public Map<String, Object> eliminarDocente(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Docente docente = docenteRepositorio.findById(id)
					.orElseThrow(() -> new RuntimeException("Docente no encontrado"));

			docente.setActivo(false); // Eliminación lógica
			docenteRepositorio.save(docente);

			response.put("success", true);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		}
		return response;
	}

	// *******************************************************************
	// METODOS PARA EDITAR Y ELIMINAR LOS REPRESENTANTES
	// ******************************************************************
	@PostMapping("/editar-representante/{id}")
	@ResponseBody
	public Map<String, Object> editarRepresentante(@PathVariable Integer id, @RequestBody Map<String, String> data) {
		Map<String, Object> response = new HashMap<>();
		try {
			Representante representante = representanteRepositorio.findById(id)
					.orElseThrow(() -> new RuntimeException("Representante no encontrado"));

			representante.setNombre(data.get("nombre"));
			representante.setApellido(data.get("apellido"));
			representante.setEmail(data.get("email"));
			representante.setTelefono(data.get("telefono"));

			representanteRepositorio.save(representante);
			response.put("success", true);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		}
		return response;
	}

	@PostMapping("/eliminar-representante/{id}")
	@ResponseBody
	public Map<String, Object> eliminarRepresentante(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Representante representante = representanteRepositorio.findById(id)
					.orElseThrow(() -> new RuntimeException("Representante no encontrado"));

			representante.setActivo(false); // 🔹 Eliminación lógica (cambia a inactivo)
			representanteRepositorio.save(representante); // 🔹 Guarda el cambio en la BD

			response.put("success", true);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		}
		return response;
	}
	
	
	// *******************************************************************
	// METODOS PARA EDITAR Y ELIMINAR LOS ESTUDIANTES 
	// ******************************************************************
	@PostMapping("/editar-estudiante/{id}")
	@ResponseBody
	public Map<String, Object> editarEstudiante(@PathVariable Integer id, @RequestBody Map<String, String> data) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        Estudiante estudiante = estudianteRepositorio.findById(id)
	                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

	        estudiante.setNombre(data.get("nombre"));
	        estudiante.setApellido(data.get("apellido"));
	        estudiante.setCedula(data.get("cedula"));
	        estudiante.setCurso(data.get("curso"));

	        estudianteRepositorio.save(estudiante);
	        response.put("success", true);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", e.getMessage());
	    }
	    return response;
	}

	// ELIMINAR ESTUDIANTE (LÓGICA)
	@PostMapping("/eliminar-estudiante/{id}")
	@ResponseBody
	public Map<String, Object> eliminarEstudiante(@PathVariable Integer id) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        Estudiante estudiante = estudianteRepositorio.findById(id)
	                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

	        estudiante.setActivo(false); // Eliminación lógica
	        estudianteRepositorio.save(estudiante);

	        response.put("success", true);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", e.getMessage());
	    }
	    return response;
	}


}
