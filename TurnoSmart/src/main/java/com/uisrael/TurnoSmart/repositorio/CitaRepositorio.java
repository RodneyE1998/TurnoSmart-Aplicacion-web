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

	//Metodos para la impresion 
	List<Cita> findByEstudianteIdEstudiante(Integer idEstudiante);

	@Query("SELECT COUNT(c) FROM Cita c JOIN c.docentes d WHERE c.estadoCita = :estado AND d.idDocente = :idDocente")
	int contarCitasPorEstadoYDocente(@Param("estado") String estado, @Param("idDocente") Integer idDocente);
	
	@Query("SELECT c FROM Cita c JOIN c.docentes d WHERE c.estudiante.idEstudiante = :idEstudiante AND d.idDocente = :idDocente")
	List<Cita> findByEstudianteIdEstudianteAndDocente(@Param("idEstudiante") Integer idEstudiante, @Param("idDocente") Integer idDocente);
	
	@Query("SELECT COUNT(c) FROM Cita c JOIN c.docentes d WHERE c.tipoCita = :tipo AND d.idDocente = :idDocente")
	long countByTipoCitaAndDocente(@Param("tipo") Cita.TipoCita tipo, @Param("idDocente") Integer idDocente);



}
