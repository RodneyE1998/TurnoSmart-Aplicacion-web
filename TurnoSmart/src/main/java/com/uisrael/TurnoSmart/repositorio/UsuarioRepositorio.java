package com.uisrael.TurnoSmart.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.TurnoSmart.modelo.Usuario;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	// Método para buscar un usuario por su nombre de usuario
	Optional<Usuario> findByUsername(String username);

}
