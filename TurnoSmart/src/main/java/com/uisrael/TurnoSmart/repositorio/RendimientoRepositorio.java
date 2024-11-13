package com.uisrael.TurnoSmart.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.Rendimiento;

public interface RendimientoRepositorio extends JpaRepository<Rendimiento, Integer> {
	// Aquí podemos agregar métodos personalizados para realizar operaciones específicas

}
