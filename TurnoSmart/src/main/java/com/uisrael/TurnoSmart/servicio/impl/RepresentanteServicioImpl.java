package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.Representante;
import com.uisrael.TurnoSmart.repositorio.RepresentanteRepositorio;
import com.uisrael.TurnoSmart.servicio.RepresentanteServicio;

@Service
@Transactional
public class RepresentanteServicioImpl implements RepresentanteServicio {
	
	@Autowired
	private RepresentanteRepositorio representanteRepository;

	@Override
	public List<Representante> getAllRepresentantes() {
		// TODO Auto-generated method stub
		return representanteRepository.findAll();
	}

	@Override
	public Representante findByIdRepresentante(Integer idRepresentante) {
	    Optional<Representante> optionalRepresentante = representanteRepository.findByIdRepresentante(idRepresentante);
	    if (optionalRepresentante.isPresent()) {
	        return optionalRepresentante.get();
	    } else {
	        throw new RuntimeException("No se encontró un docente con el número de identificación " + idRepresentante);
	    }
	}

	@Override
	public Representante createRepresentante(Representante representante) {
		// TODO Auto-generated method stub
		return representanteRepository.save(representante);
	}

	@Override
	public Representante updateRepresentante(Representante representante) {
		// TODO Auto-generated method stub
		return representanteRepository.save(representante);
	}

	@Override
	public void deleteRepresentante(Integer idRepresentante) {
	    representanteRepository.deleteByIdRepresentante(idRepresentante);
	}

}
