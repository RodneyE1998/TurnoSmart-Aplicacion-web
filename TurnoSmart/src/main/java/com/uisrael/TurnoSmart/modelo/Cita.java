package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	//@Temporal(TemporalType.DATE)  
    private LocalDate fechaCita;
	//@Temporal(TemporalType.TIME) 
    private LocalTime  horaCita;
    private String estadoCita;

    @ManyToOne
    @JoinColumn(name = "id_representante")
    @ToString.Exclude
    private Representante representante;
    
    @ManyToMany
    @JoinTable(
        name = "citas_docentes",
        joinColumns = @JoinColumn(name = "id_cita"),
        inverseJoinColumns = @JoinColumn(name = "id_docente")
    )
    @ToString.Exclude
    private List<Docente> docentes;
    
    @ManyToMany
    @JoinTable(
        name = "citas_representantes",
        joinColumns = @JoinColumn(name = "id_cita"),
        inverseJoinColumns = @JoinColumn(name = "id_representante")
    )
    private List<Representante> representantes;
    
    @OneToMany(mappedBy = "cita")
    @ToString.Exclude
    private List<HistorialCita> historialCitas;
    
    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

}
