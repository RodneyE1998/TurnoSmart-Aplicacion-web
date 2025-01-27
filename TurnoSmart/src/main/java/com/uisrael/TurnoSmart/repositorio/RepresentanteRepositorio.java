package com.uisrael.TurnoSmart.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.Representante;

public interface RepresentanteRepositorio extends JpaRepository<Representante, Integer> {
	// Aquí podemos agregar métodos personalizados para realizar operaciones específicas
	
	Optional<Representante> findByIdRepresentante(Integer idRepresentante);
    
    void deleteByIdRepresentante(Integer idRepresentante);
    
    Optional<Representante> findByUsuarioUsername(String username);

    
 

}
