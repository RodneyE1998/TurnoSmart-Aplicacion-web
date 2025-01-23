package com.uisrael.TurnoSmart.repositorio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Docente;

public interface CitaRepositorio extends JpaRepository<Cita, Integer> {
	
	List<Cita> findByDocentesIdDocente(Integer idDocente);
	
	 boolean existsByFechaCitaAndHoraCitaAndDocentes(LocalDate fechaCita, LocalTime horaCita, Docente docente);
	 
	 List<Cita> findByRepresentanteIdRepresentante(Integer idRepresentante);
}
