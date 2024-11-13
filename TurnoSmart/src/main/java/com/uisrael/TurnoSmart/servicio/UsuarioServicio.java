package com.uisrael.TurnoSmart.servicio;

import java.util.Optional;

import com.uisrael.TurnoSmart.modelo.Usuario;


public interface UsuarioServicio {
	
	Optional<Usuario> encontrarPorUsername(String username);
	
	void guardarUsuario(Usuario usuario);

}
