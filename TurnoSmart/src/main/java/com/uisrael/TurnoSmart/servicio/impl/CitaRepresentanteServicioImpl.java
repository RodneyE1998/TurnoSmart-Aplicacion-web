package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.CitaRepresentante;
import com.uisrael.TurnoSmart.repositorio.CitaRepresentanteRepositorio;
import com.uisrael.TurnoSmart.servicio.CitaRepresentanteServicio;

@Service
@Transactional
public class CitaRepresentanteServicioImpl implements CitaRepresentanteServicio {
	
	@Autowired
    private CitaRepresentanteRepositorio citaRepresentanteRepository;

	@Override
	public List<CitaRepresentante> getAllCitasRepresentantes() {
		// TODO Auto-generated method stub
		return citaRepresentanteRepository.findAll();
	}

	@Override
	public CitaRepresentante getCitaRepresentanteById(Integer id) {
		// TODO Auto-generated method stub
		return citaRepresentanteRepository.findById(id).orElseThrow();
	}

	@Override
	public CitaRepresentante createCitaRepresentante(CitaRepresentante citaRepresentante) {
		// TODO Auto-generated method stub
		return citaRepresentanteRepository.save(citaRepresentante);
	}

	@Override
	public CitaRepresentante updateCitaRepresentante(CitaRepresentante citaRepresentante) {
		// TODO Auto-generated method stub
		return citaRepresentanteRepository.save(citaRepresentante);
	}

	@Override
	public void deleteCitaRepresentante(Integer id) {
		// TODO Auto-generated method stub
		 citaRepresentanteRepository.deleteById(id);
	}

}
