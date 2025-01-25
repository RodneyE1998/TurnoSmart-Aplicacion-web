package com.uisrael.TurnoSmart.servicio.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.repositorio.CitaRepositorio;
import com.uisrael.TurnoSmart.repositorio.DocenteRepositorio;
import com.uisrael.TurnoSmart.servicio.DocenteServicio;

@Service
@Transactional
public class DocenteServicioImpl implements DocenteServicio{
	
	@Autowired
    private DocenteRepositorio docenteRepository;

	@Override
	public List<Docente> getAllDocentes() {
		// TODO Auto-generated method stub
		return docenteRepository.findAll();
	}

	@Override
	public Docente getDocenteByNumeroIdentificacion(String numeroIdentificacion) {
	    Docente docente = docenteRepository.findByNumeroIdentificacion(numeroIdentificacion);
	    if (docente == null) {
	        throw new RuntimeException("No se encontró un docente con el número de identificación " + numeroIdentificacion);
	    }
	    return docente;
	}

	@Override
	public Docente createDocente(Docente docente) {
		return docenteRepository.save(docente);
	}

	@Override
	public Docente updateDocente(Docente docente) {
		return docenteRepository.save(docente);
	}

	@Override
	public void deleteDocente(String numeroIdentificacion) {
		docenteRepository.deleteByNumeroIdentificacion(numeroIdentificacion);
	}

	@Override
	public List<Docente> obtenerDocentesPorEstudiante(Integer idEstudiante) {
		// TODO Auto-generated method stub
		return docenteRepository.findByEstudiantesIdEstudiante(idEstudiante);
	}

	@Override
    public List<Docente> obtenerTodosLosDocentes() {
        return docenteRepository.findAll();
    }

	

}
