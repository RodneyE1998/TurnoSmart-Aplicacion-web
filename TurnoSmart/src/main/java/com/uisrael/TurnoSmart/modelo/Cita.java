package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Cita implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCita;
	// @Temporal(TemporalType.DATE)
	private LocalDate fechaCita;
	// @Temporal(TemporalType.TIME)
	private LocalTime horaCita;
	private String estadoCita;
	@Column(length = 500)
	private String motivoCita;
	@Enumerated(EnumType.STRING)
	private TipoCita tipoCita;

	@ManyToOne
	@JoinColumn(name = "id_representante")
	@JsonBackReference
	@ToString.Exclude
	private Representante representante;

	@ManyToMany
	@JoinTable(name = "citas_docentes", joinColumns = @JoinColumn(name = "id_cita"), inverseJoinColumns = @JoinColumn(name = "id_docente"))
	@ToString.Exclude
	private List<Docente> docentes;

	@ManyToOne
	@JoinColumn(name = "id_estudiante")
	@JsonBackReference
	@ToString.Exclude
	private Estudiante estudiante;

	// Campo transitorio para nombres concatenados de docentes
	@Transient
	private String docentesConcatenados;

	public String getDocentesConcatenados() {
		return docentesConcatenados;
	}

	public void setDocentesConcatenados(String docentesConcatenados) {
		this.docentesConcatenados = docentesConcatenados;
	}

	// Enum para definir los tipos de citas
	public enum TipoCita {
		ACADEMICO, DISCIPLINARIO
	}

}
