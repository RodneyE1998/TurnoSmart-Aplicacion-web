package com.uisrael.TurnoSmart.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.HorarioDisponible;

public interface HorarioDisponibleRepositorio extends JpaRepository<HorarioDisponible, Integer>{

	List<HorarioDisponible> findByDocenteIdDocente(Integer idDocente);

}
