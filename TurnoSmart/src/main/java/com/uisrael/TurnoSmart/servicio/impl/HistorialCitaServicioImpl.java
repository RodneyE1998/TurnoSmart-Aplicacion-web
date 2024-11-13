package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.HistorialCita;
import com.uisrael.TurnoSmart.repositorio.HistorialCitaRepositorio;
import com.uisrael.TurnoSmart.servicio.HistorialCitaServicio;

@Service
@Transactional
public class HistorialCitaServicioImpl implements HistorialCitaServicio {
	
	 @Autowired
	 private HistorialCitaRepositorio historialCitaRepository;

	@Override
	public List<HistorialCita> getAllHistorialCitas() {
		// TODO Auto-generated method stub
		return historialCitaRepository.findAll();
	}

	@Override
	public HistorialCita getHistorialCitaById(Integer id) {
		// TODO Auto-generated method stub
		return  historialCitaRepository.findById(id).orElseThrow();
	}

	@Override
	public HistorialCita createHistorialCita(HistorialCita historialCita) {
		// TODO Auto-generated method stub
		return historialCitaRepository.save(historialCita);
	}

	@Override
	public HistorialCita updateHistorialCita(HistorialCita historialCita) {
		// TODO Auto-generated method stub
		return historialCitaRepository.save(historialCita);
	}

	@Override
	public void deleteHistorialCita(Integer id) {
		// TODO Auto-generated method stub
		historialCitaRepository.deleteById(id);
	}

}
