package com.uisrael.TurnoSmart.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.modelo.Representante;

public interface RepresentanteRepositorio extends JpaRepository<Representante, Integer> {
	// Aquí podemos agregar métodos personalizados para realizar operaciones específicas
	
	Optional<Representante> findByIdRepresentante(Integer idRepresentante);
    
    void deleteByIdRepresentante(Integer idRepresentante);
    
    Optional<Representante> findByUsuarioUsername(String username);
    
    Representante findById(int idRepresentante);
    
    @Query(value = """
    	    SELECT d.id_docente, d.nombre, d.apellido, d.email, d.telefono, d.numero_identificacion
    	    FROM docente d
    	    JOIN estudiantes_docentes ed ON d.id_docente = ed.id_docente
    	    JOIN estudiante e ON ed.id_estudiante = e.id_estudiante
    	    WHERE e.id_representante = :idRepresentante
    	    """, nativeQuery = true)
    	List<Object[]> obtenerDocentesRaw(@Param("idRepresentante") Integer idRepresentante);








}
