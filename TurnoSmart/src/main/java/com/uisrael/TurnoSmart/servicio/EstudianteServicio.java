package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.Estudiante;

public interface EstudianteServicio {
	Estudiante crearEstudiante(Estudiante estudiante);

	List<Estudiante> listarEstudiantes();

	Estudiante buscarEstudiantePorId(Integer id);
	
	public List<Estudiante> obtenerEstudiantesPorRepresentante(Integer idRepresentante);
	
	public List<Estudiante> obtenerEstudiantesPorDocente(Integer idDocente);



}
