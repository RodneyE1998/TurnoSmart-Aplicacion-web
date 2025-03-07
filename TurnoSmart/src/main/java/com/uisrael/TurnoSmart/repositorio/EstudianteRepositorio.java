package com.uisrael.TurnoSmart.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uisrael.TurnoSmart.modelo.Estudiante;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Integer> {
	
	@Query("SELECT e FROM Estudiante e WHERE e.representante.idRepresentante = :idRepresentante")
	List<Estudiante> findByRepresentanteIdRepresentante(@Param("idRepresentante") Integer idRepresentante);

	
	@Query("SELECT e FROM Estudiante e JOIN e.docentes d WHERE d.idDocente = :idDocente")
    List<Estudiante> findByDocenteId(@Param("idDocente") Integer idDocente);
	
	 List<Estudiante> findByActivoTrue();

}
