package com.uisrael.TurnoSmart.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.TurnoSmart.modelo.Estudiante;
import com.uisrael.TurnoSmart.servicio.EstudianteServicio;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteControlador {

	@Autowired
	private EstudianteServicio estudianteServicio;
	
	@PostMapping
    public Estudiante crearEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteServicio.crearEstudiante(estudiante);
    }

    @GetMapping
    public List<Estudiante> listarEstudiantes() {
        return estudianteServicio.listarEstudiantes();
    }

    @GetMapping("/{id}")
    public Estudiante buscarEstudiantePorId(@PathVariable Integer id) {
        return estudianteServicio.buscarEstudiantePorId(id);
    }
    
    @GetMapping("/representante/{idRepresentante}/estudiantes")
    public List<Estudiante> obtenerEstudiantesPorRepresentante(@PathVariable Integer idRepresentante) {
        return estudianteServicio.obtenerEstudiantesPorRepresentante(idRepresentante);
    }


}
