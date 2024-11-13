package com.uisrael.TurnoSmart.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uisrael.TurnoSmart.modelo.Usuario;
import com.uisrael.TurnoSmart.repositorio.UsuarioRepositorio;
import com.uisrael.TurnoSmart.servicio.UserDetailsServicio;

@Service
public class UserDetailsServicioImpl implements UserDetailsServicio {
	
	@Autowired
	@Lazy
    private UsuarioRepositorio usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Usuario usuario = usuarioRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
	    return usuario; // Aquí devolvemos el usuario después de asegurarnos que existe
	}


}


