package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Docente implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDocente;
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String numeroIdentificacion;

	@OneToMany(mappedBy = "docente", fetch = FetchType.EAGER)
	private List<HorarioDisponible> horariosDisponibles;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "citas_docentes", joinColumns = @JoinColumn(name = "id_docente"), inverseJoinColumns = @JoinColumn(name = "id_cita"))
	@ToString.Exclude
	private List<Cita> citas;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rendimientos_docentes", joinColumns = @JoinColumn(name = "id_docente"), inverseJoinColumns = @JoinColumn(name = "id_rendimiento"))
	@ToString.Exclude
	private List<Rendimiento> rendimientos;

	@ManyToMany(mappedBy = "docentes")
	@ToString.Exclude
	private List<Estudiante> estudiantes;

}
