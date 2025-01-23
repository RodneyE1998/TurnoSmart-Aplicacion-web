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
public class HorarioDisponibleServicioImpl implements HorarioDisponibleServicio {

	@Autowired
	private HorarioDisponibleRepositorio horarioRepositorio;

	@Override
	public HorarioDisponible guardarHorario(HorarioDisponible horario) {
		// TODO Auto-generated method stub
		return horarioRepositorio.save(horario);
	}

	@Override
	public void eliminarHorario(Integer idHorario) {
		// TODO Auto-generated method stub
		horarioRepositorio.deleteById(idHorario);
	}

	@Override
	public List<HorarioDisponible> obtenerHorariosPorDocente(Integer idDocente) {
		// TODO Auto-generated method stub
		return horarioRepositorio.findByDocenteIdDocente(idDocente);
	}

	@Override
	public List<HorarioDisponible> obtenerTodosLosHorarios() {
		return horarioRepositorio.findAll();
	}

	@Override
	public HorarioDisponible obtenerPorId(Integer idHorario) {
		return horarioRepositorio.findById(idHorario)
				.orElseThrow(() -> new IllegalArgumentException("Horario no encontrado con ID: " + idHorario));
	}

}
