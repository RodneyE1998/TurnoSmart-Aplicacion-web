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

import com.uisrael.TurnoSmart.modelo.CitaRepresentante;
import com.uisrael.TurnoSmart.servicio.CitaRepresentanteServicio;

@RestController
@RequestMapping("/api/citas-representantes")
public class CitaRepresentanteControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private CitaRepresentanteServicio citaRepresentanteService;

    @GetMapping
    public List<CitaRepresentante> getAllCitasRepresentantes() {
        return citaRepresentanteService.getAllCitasRepresentantes();
    }

    @GetMapping("/{id}")
    public CitaRepresentante getCitaRepresentanteById(@PathVariable Integer id) {
        return citaRepresentanteService.getCitaRepresentanteById(id);
    }

    @PostMapping
    public CitaRepresentante createCitaRepresentante(@RequestBody CitaRepresentante citaRepresentante) {
        return citaRepresentanteService.createCitaRepresentante(citaRepresentante);
    }
    
    @PutMapping("/{id}")
    public CitaRepresentante updateCitaRepresentante(@PathVariable Integer id, @RequestBody CitaRepresentante citaRepresentante) {
        return citaRepresentanteService.updateCitaRepresentante(citaRepresentante);
    }

    @DeleteMapping("/{id}")
    public void deleteCitaRepresentante(@PathVariable Integer id) {
        citaRepresentanteService.deleteCitaRepresentante(id);
    }

}
