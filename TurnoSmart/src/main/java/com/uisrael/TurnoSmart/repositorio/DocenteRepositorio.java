package com.uisrael.TurnoSmart.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uisrael.TurnoSmart.modelo.Docente;

public interface DocenteRepositorio extends JpaRepository<Docente, Integer> {
	
	Docente findByNumeroIdentificacion(String numeroIdentificacion);
    
    void deleteByNumeroIdentificacion(String numeroIdentificacion);
    
 // Método para encontrar docentes por el ID del estudiante
    List<Docente> findByEstudiantesIdEstudiante(Integer idEstudiante);
    
    List<Docente> findAll(); 
    
    @Query("SELECT DISTINCT d FROM Docente d " +
            "JOIN d.citas c " +
            "JOIN c.representante r " +
            "WHERE r.idRepresentante = :idRepresentante")
     List<Docente> findDocentesByRepresentante(@Param("idRepresentante") Integer idRepresentante);
    
    List<Docente> findByActivoTrue();


}
