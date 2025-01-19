package com.uisrael.TurnoSmart.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.Cita;

public interface CitaRepositorio extends JpaRepository<Cita, Integer> {
	
	List<Cita> findByDocentesIdDocente(Integer idDocente);


}
