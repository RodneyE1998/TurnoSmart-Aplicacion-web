package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uisrael.TurnoSmart.modelo.Estudiante;
import com.uisrael.TurnoSmart.repositorio.EstudianteRepositorio;
import com.uisrael.TurnoSmart.servicio.EstudianteServicio;

@Service
public class EstudianteServicioImpl implements EstudianteServicio {

	@Autowired
	private EstudianteRepositorio estudianteRepositorio;

	@Override
	public Estudiante crearEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return estudianteRepositorio.save(estudiante);
	}

	@Override
	public List<Estudiante> listarEstudiantes() {
		// TODO Auto-generated method stub
		return estudianteRepositorio.findAll();
	}

	@Override
	public Estudiante buscarEstudiantePorId(Integer id) {
		// TODO Auto-generated method stub
		return estudianteRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
	}

	@Override
	public List<Estudiante> obtenerEstudiantesPorRepresentante(Integer idRepresentante) {
		// TODO Auto-generated method stub
		return estudianteRepositorio.findByRepresentanteIdRepresentante(idRepresentante);
	}

}
