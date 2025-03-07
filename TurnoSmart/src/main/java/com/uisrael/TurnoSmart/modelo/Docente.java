package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
	
	@Column(nullable = false)
	private Boolean activo = true;

	 @OneToMany(mappedBy = "docente", fetch = FetchType.LAZY)
	    @JsonIgnore
	    @ToString.Exclude
	    private List<HorarioDisponible> horariosDisponibles;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "citas_docentes", joinColumns = @JoinColumn(name = "id_docente"), inverseJoinColumns = @JoinColumn(name = "id_cita"))
	@JsonBackReference
    @ToString.Exclude
	private List<Cita> citas;

	@ManyToMany(mappedBy = "docentes")
	@JsonBackReference
    @ToString.Exclude
	private List<Estudiante> estudiantes;
	
	// Getters y Setters
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
