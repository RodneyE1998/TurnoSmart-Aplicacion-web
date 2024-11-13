package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Representante implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRepresentante;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String cedula;
    
    @OneToMany(mappedBy = "representante")
    private List<Cita> citas;
    
    /*@ManyToMany
    @JoinTable(
        name = "citas_representantes",
        joinColumns = @JoinColumn(name = "id_representante"),
        inverseJoinColumns = @JoinColumn(name = "id_cita")
    )
    private List<Cita> citasRepresentantes;*/
    
    @ManyToMany
    @JoinTable(
        name = "rendimientos_representantes",
        joinColumns = @JoinColumn(name = "id_representante"),
        inverseJoinColumns = @JoinColumn(name = "id_rendimiento")
    )
    @ToString.Exclude
    private List<Rendimiento> rendimientos;
}
