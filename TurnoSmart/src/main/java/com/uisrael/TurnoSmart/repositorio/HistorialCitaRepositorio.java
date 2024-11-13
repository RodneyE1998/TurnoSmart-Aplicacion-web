package com.uisrael.TurnoSmart.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.HistorialCita;

public interface HistorialCitaRepositorio extends JpaRepository<HistorialCita, Integer> {
	// Aquí podemos agregar métodos personalizados para realizar operaciones específicas

}
