package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Role implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;
    private String nombre;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER) 
    @ToString.Exclude
    private List<Usuario> usuarios;
    
    public String getNombre() {
        return nombre;
    }

}
