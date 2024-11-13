package com.uisrael.TurnoSmart.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/docente")
public class DocenteControlador {

    @GetMapping("/PrincipalDocente")
    public String mostrarPaginaDocente() {
    	return "PrincipalDocente";
    }

   

}
