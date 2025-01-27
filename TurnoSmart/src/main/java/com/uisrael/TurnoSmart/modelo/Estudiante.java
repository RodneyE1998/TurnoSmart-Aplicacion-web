package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Estudiante implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEstudiante;
	private String nombre;
	private String apellido;
	private String curso;
	private String cedula;

	@ManyToOne
	@JoinColumn(name = "id_representante")
	@JsonBackReference
    @ToString.Exclude
	private Representante representante;

	@ManyToMany
	@JoinTable(name = "estudiantes_docentes", joinColumns = @JoinColumn(name = "id_estudiante"), inverseJoinColumns = @JoinColumn(name = "id_docente"))
	@ToString.Exclude
	private List<Docente> docentes;

}
