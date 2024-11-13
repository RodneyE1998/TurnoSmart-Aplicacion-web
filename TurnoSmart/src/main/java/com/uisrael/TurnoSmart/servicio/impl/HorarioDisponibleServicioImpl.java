package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.HorarioDisponible;
import com.uisrael.TurnoSmart.repositorio.HorarioDisponibleRepositorio;
import com.uisrael.TurnoSmart.servicio.HorarioDisponibleServicio;

@Service
@Transactional
public class HorarioDisponibleServicioImpl implements HorarioDisponibleServicio{
	
	@Autowired
	private HorarioDisponibleRepositorio horarioDisponibleRepository;

	@Override
	public List<HorarioDisponible> getAllHorariosDisponibles() {
		// TODO Auto-generated method stub
		return horarioDisponibleRepository.findAll();
	}

	@Override
	public HorarioDisponible getHorarioDisponibleById(Integer id) {
		// TODO Auto-generated method stub
		return horarioDisponibleRepository.findById(id).orElseThrow();
	}

	@Override
	public HorarioDisponible createHorarioDisponible(HorarioDisponible horarioDisponible) {
		// TODO Auto-generated method stub
		return horarioDisponibleRepository.save(horarioDisponible);
	}

	@Override
	public HorarioDisponible updateHorarioDisponible(HorarioDisponible horarioDisponible) {
		// TODO Auto-generated method stub
		return horarioDisponibleRepository.save(horarioDisponible);
	}

	@Override
	public void deleteHorarioDisponible(Integer id) {
		// TODO Auto-generated method stub
		horarioDisponibleRepository.deleteById(id);
	}

}
