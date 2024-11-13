package com.uisrael.TurnoSmart.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.Role;
import com.uisrael.TurnoSmart.repositorio.RoleRepositorio;
import com.uisrael.TurnoSmart.servicio.RoleServicio;

@Service
@Transactional
public class RoleServicioImpl implements RoleServicio {
	
	@Autowired
    private RoleRepositorio roleRepositorio;

    @Override
    public Role encontrarPorNombre(String nombre) {
        // Método para encontrar un role por su nombre
        return roleRepositorio.findByNombre(nombre);
    }

    @Override
    public List<Role> listarRoles() {
        // Método para listar todos los roles
        return roleRepositorio.findAll();
    }

    @Override
    public void guardarRole(Role role) {
        // Método para guardar un nuevo role
        roleRepositorio.save(role);
    }

}
