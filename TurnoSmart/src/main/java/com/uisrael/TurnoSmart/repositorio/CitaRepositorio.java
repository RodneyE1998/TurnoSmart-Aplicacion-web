package com.uisrael.TurnoSmart.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.Cita;

public interface CitaRepositorio extends JpaRepository<Cita, Integer> {
	
	// Aquí podemos agregar métodos personalizados para realizar operaciones específicas

}
