package com.uisrael.TurnoSmart.controlador;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.servicio.CitaServicio;


@RestController
@RequestMapping("/api/citas")
public class CitaControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CitaServicio citaService;
	
	@GetMapping
    public List<Cita> getAllCitas() {
        return citaService.getAllCitas();
    }

    @GetMapping("/{id}")
    public Cita getCitaById(@PathVariable Integer id) {
        return citaService.getCitaById(id);
    }

    @PostMapping
    public Cita createCita(@RequestBody Cita cita) {
        return citaService.createCita(cita);
    }
    
    @PutMapping("/{id}")
    public Cita updateCita(@PathVariable Integer id, @RequestBody Cita cita) {
        return citaService.updateCita(cita);
    }

    @DeleteMapping("/{id}")
    public void deleteCita(@PathVariable Integer id) {
        citaService.deleteCita(id);
    }
	
	
}
