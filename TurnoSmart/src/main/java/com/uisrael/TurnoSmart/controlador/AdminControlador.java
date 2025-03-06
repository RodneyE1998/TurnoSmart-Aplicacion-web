package com.uisrael.TurnoSmart.controlador;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.modelo.Estudiante;
import com.uisrael.TurnoSmart.modelo.Representante;
import com.uisrael.TurnoSmart.modelo.Role;
import com.uisrael.TurnoSmart.modelo.Usuario;
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
	private final BCryptPasswordEncoder passwordEncoder;

	public AdminControlador(DocenteRepositorio docenteRepositorio, RepresentanteRepositorio representanteRepositorio,
			EstudianteRepositorio estudianteRepositorio, UsuarioRepositorio usuarioRepositorio,
			RoleRepositorio roleRepositorio, BCryptPasswordEncoder passwordEncoder) {
		this.docenteRepositorio = docenteRepositorio;
		this.representanteRepositorio = representanteRepositorio;
		this.estudianteRepositorio = estudianteRepositorio;
		this.usuarioRepositorio = usuarioRepositorio;
		this.roleRepositorio = roleRepositorio;
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

	// CREAR DOCENTE + USUARIO AUTOMÁTICO
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

	// CREAR REPRESENTANTE + USUARIO AUTOMÁTICO
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

	
	// CREAR ESTUDIANTE Y ASIGNARLO A UN REPRESENTANTE Y DOCENTES
	@PostMapping("/crear-estudiante")
	public String crearEstudiante(@RequestParam String nombre, 
	                              @RequestParam String apellido,
	                              @RequestParam String cedula, 
	                              @RequestParam String curso, 
	                              @RequestParam Integer idRepresentante, 
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
	

}
