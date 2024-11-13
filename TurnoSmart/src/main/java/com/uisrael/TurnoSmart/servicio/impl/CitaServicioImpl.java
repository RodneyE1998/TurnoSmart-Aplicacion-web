package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.repositorio.CitaRepositorio;
import com.uisrael.TurnoSmart.servicio.CitaServicio;

@Service
@Transactional
public class CitaServicioImpl implements CitaServicio {
	
	@Autowired
    private CitaRepositorio citaRepository;

	@Override
	public List<Cita> getAllCitas() {
		// TODO Auto-generated method stub
		return citaRepository.findAll();
	}

	@Override
	public Cita getCitaById(Integer id) {
		// TODO Auto-generated method stub
		return citaRepository.findById(id).orElseThrow();
	}

	@Override
	public Cita createCita(Cita cita) {
		// TODO Auto-generated method stub
		return citaRepository.save(cita);
	}

	@Override
	public Cita updateCita(Cita cita) {
		// TODO Auto-generated method stub
		return citaRepository.save(cita);
	}

	@Override
	public void deleteCita(Integer id) {
		// TODO Auto-generated method stub
		citaRepository.deleteById(id);
	}
	
	

}
