package com.uisrael.TurnoSmart.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.HorarioDisponible;

public interface HorarioDisponibleRepositorio extends JpaRepository<HorarioDisponible, Integer>{

	// Aquí podemos agregar métodos personalizados para realizar operaciones específicas
}
