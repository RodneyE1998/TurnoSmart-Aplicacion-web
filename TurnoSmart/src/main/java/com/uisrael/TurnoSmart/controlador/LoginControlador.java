package com.uisrael.TurnoSmart.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControlador {

	@GetMapping("/login")
	public String mostrarLogin() {
		return "Login"; 
	}

	@GetMapping("/default")
	public String defaultAfterLogin(Authentication authentication) {
		authentication.getAuthorities().forEach(auth -> System.out.println("Authority: " + auth.getAuthority()));
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("DOCENTE"))) {
			return "redirect:/docente/PrincipalDocente";
		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REPRESENTANTE"))) {
			return "redirect:/representante/PrincipalRepresentante";
		}
		return "redirect:/login";
	}
}
