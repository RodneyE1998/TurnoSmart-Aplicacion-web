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

import com.uisrael.TurnoSmart.modelo.Rendimiento;
import com.uisrael.TurnoSmart.servicio.RendimientoServicio;

@RestController
@RequestMapping("/api/rendimientos")
public class RendimientoControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 	@Autowired
	    private RendimientoServicio rendimientoService;

	    @GetMapping
	    public List<Rendimiento> getAllRendimientos() {
	        return rendimientoService.getAllRendimientos();
	    }

	    @GetMapping("/{id}")
	    public Rendimiento getRendimientoById(@PathVariable Integer id) {
	        return rendimientoService.getRendimientoById(id);
	    }
	    
	    @PostMapping
	    public Rendimiento createRendimiento(@RequestBody Rendimiento rendimiento) {
	        return rendimientoService.createRendimiento(rendimiento);
	    }

	    @PutMapping("/{id}")
	    public Rendimiento updateRendimiento(@PathVariable Integer id, @RequestBody Rendimiento rendimiento) {
	        return rendimientoService.updateRendimiento(rendimiento);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteRendimiento(@PathVariable Integer id) {
	        rendimientoService.deleteRendimiento(id);
	    }

}
