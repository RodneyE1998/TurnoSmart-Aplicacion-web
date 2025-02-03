package com.uisrael.TurnoSmart.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.TurnoSmart.servicio.CitaServicio;

@Controller
@RequestMapping({"/docente", "/representante"})
public class CitaControlador {

	 @Autowired
	    private CitaServicio citaServicio;

	 @GetMapping("/confirmar-cita/{idCita}")
	 public String confirmarCitaDesdeCorreo(@PathVariable Integer idCita, Model model) {
	     try {
	         citaServicio.confirmarCita(idCita);
	         model.addAttribute("mensaje", "¡Su cita ha sido confirmada exitosamente!");
	         return "ConfirmacionExitosa"; // Retorna la vista HTML
	     } catch (Exception e) {
	         model.addAttribute("mensaje", "Hubo un error al confirmar la cita: " + e.getMessage());
	         return "ErrorConfirmacion"; // Retorna la vista de error
	     }
	 }



}
