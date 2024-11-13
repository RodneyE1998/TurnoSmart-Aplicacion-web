package com.uisrael.TurnoSmart.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pagina-principal")
public class PaginaPrincipalControlador {
	
	@GetMapping
    public String mostrarPaginaPrincipal() {
        return "PaginaPrincipal"; // Asegúrate de que esto coincida con el nombre del archivo HTML
    }

}
