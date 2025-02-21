package com.uisrael.TurnoSmart.servicio.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
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
	    return new UserDetailsImpl(usuario); // 🔹 Retornar la nueva subclase con los datos del usuario
	}

	// 🔹 Subclase que implementa UserDetails y permite acceder al id_representante
	public static class UserDetailsImpl implements UserDetails {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final Usuario usuario;

	    public UserDetailsImpl(Usuario usuario) {
	        this.usuario = usuario;
	    }

	    public int getIdRepresentante() {
	        return (usuario.getIdRepresentante() != null) ? usuario.getIdRepresentante() : 0;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return usuario.getRoles().stream()
	                .map(role -> (GrantedAuthority) () -> role.getNombre())
	                .collect(Collectors.toList());
	    }

	    @Override
	    public String getPassword() {
	        return usuario.getPassword();
	    }

	    @Override
	    public String getUsername() {
	        return usuario.getUsername();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	}


}

