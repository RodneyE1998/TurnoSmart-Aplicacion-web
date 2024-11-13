package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.Role;

public interface RoleServicio {
	
	 Role encontrarPorNombre(String nombre);
	 List<Role> listarRoles();
	 void guardarRole(Role role);

}
