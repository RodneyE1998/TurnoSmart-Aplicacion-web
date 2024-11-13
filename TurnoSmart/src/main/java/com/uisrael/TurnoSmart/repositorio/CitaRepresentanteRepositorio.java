package com.uisrael.TurnoSmart.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.CitaRepresentante;

public interface CitaRepresentanteRepositorio extends JpaRepository<CitaRepresentante, Integer> {
	// Aquí podemos agregar métodos personalizados para realizar operaciones específicas

}
