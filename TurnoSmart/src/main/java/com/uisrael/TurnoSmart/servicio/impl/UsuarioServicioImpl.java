package com.uisrael.TurnoSmart.servicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.Usuario;
import com.uisrael.TurnoSmart.repositorio.UsuarioRepositorio;
import com.uisrael.TurnoSmart.servicio.UsuarioServicio;

@Service
@Transactional
public class UsuarioServicioImpl implements UsuarioServicio {
	
	  @Autowired
	    private UsuarioRepositorio usuarioRepositorio;

	    @Override
	    public Optional<Usuario> encontrarPorUsername(String username) {
	        return usuarioRepositorio.findByUsername(username);
	    }

	    @Override
	    public void guardarUsuario(Usuario usuario) {
	        usuarioRepositorio.save(usuario);
	    }

}
