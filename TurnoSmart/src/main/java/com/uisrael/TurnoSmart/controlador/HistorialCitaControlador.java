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

import com.uisrael.TurnoSmart.modelo.HistorialCita;
import com.uisrael.TurnoSmart.servicio.HistorialCitaServicio;

@RestController
@RequestMapping("/api/historial-citas")
public class HistorialCitaControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private HistorialCitaServicio historialCitaService;

    @GetMapping
    public List<HistorialCita> getAllHistorialCitas() {
        return historialCitaService.getAllHistorialCitas();
    }

    @GetMapping("/{id}")
    public HistorialCita getHistorialCitaById(@PathVariable Integer id) {
        return historialCitaService.getHistorialCitaById(id);
    }

    @PostMapping
    public HistorialCita createHistorialCita(@RequestBody HistorialCita historialCita) {
        return historialCitaService.createHistorialCita(historialCita);
    }
    
    @PutMapping("/{id}")
    public HistorialCita updateHistorialCita(@PathVariable Integer id, @RequestBody HistorialCita historialCita) {
        return historialCitaService.updateHistorialCita(historialCita);
    }

    @DeleteMapping("/{id}")
    public void deleteHistorialCita(@PathVariable Integer id) {
        historialCitaService.deleteHistorialCita(id);
    }
    
}
