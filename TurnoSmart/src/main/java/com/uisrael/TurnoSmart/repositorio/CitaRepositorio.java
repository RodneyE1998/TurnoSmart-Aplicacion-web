package com.uisrael.TurnoSmart.repositorio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Docente;

public interface CitaRepositorio extends JpaRepository<Cita, Integer> {

	List<Cita> findByDocentesIdDocente(Integer idDocente);

	boolean existsByFechaCitaAndHoraCitaAndDocentes(LocalDate fechaCita, LocalTime horaCita, Docente docente);

	List<Cita> findByRepresentanteIdRepresentante(Integer idRepresentante);

	@Query("SELECT c FROM Cita c WHERE c.representante.idRepresentante = :idRepresentante AND c.fechaCita = :fecha AND c.horaCita = :hora")
	Optional<Cita> findByRepresentanteAndFechaCitaAndHoraCita(@Param("idRepresentante") Integer idRepresentante,
			@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);

}
