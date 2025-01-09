package com.uisrael.TurnoSmart.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/representante")
public class RepresentanteControlador {

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
	
	@GetMapping("/Citas")
	public String mostrarPaginacitas() {
	    return "CitasRepresentantes";
	}
	
	@GetMapping("/Perfil-Representante")
	public String mostrarPerfilRepresentante() {
	    return "PerfilRepresentante";
	}

    

}
