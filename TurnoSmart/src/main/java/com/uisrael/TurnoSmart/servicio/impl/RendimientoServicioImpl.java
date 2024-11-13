package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.Rendimiento;
import com.uisrael.TurnoSmart.repositorio.RendimientoRepositorio;
import com.uisrael.TurnoSmart.servicio.RendimientoServicio;

@Service
@Transactional
public class RendimientoServicioImpl implements RendimientoServicio{
	
	@Autowired
	private RendimientoRepositorio rendimientoRepository;

	@Override
	public List<Rendimiento> getAllRendimientos() {
		// TODO Auto-generated method stub
		return rendimientoRepository.findAll();
	}

	@Override
	public Rendimiento getRendimientoById(Integer id) {
		// TODO Auto-generated method stub
		return rendimientoRepository.findById(id).orElseThrow();
	}

	@Override
	public Rendimiento createRendimiento(Rendimiento rendimiento) {
		// TODO Auto-generated method stub
		return rendimientoRepository.save(rendimiento);
	}

	@Override
	public Rendimiento updateRendimiento(Rendimiento rendimiento) {
		// TODO Auto-generated method stub
		return rendimientoRepository.save(rendimiento);
	}

	@Override
	public void deleteRendimiento(Integer id) {
		// TODO Auto-generated method stub
		rendimientoRepository.deleteById(id);
	}

}
