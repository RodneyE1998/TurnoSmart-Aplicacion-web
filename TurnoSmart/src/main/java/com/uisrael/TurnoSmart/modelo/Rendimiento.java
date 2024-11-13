package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Entity
@Data
public class Rendimiento implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRendimiento;
	//@Temporal(TemporalType.DATE)
    private LocalDate fechaInicio;
	//@Temporal(TemporalType.DATE)
    private LocalDate fechaFin;
    private Integer totalCitasRealizadas;
    private Integer totalCitasCanceladas;
    private Integer totalCitasReprogramadas;
    @Enumerated(EnumType.STRING)
    private EstadoRendimiento estadoRendimiento;    
    public enum EstadoRendimiento {
        ACTIVO,
        FINALIZADO,
        PENDIENTE
    }
    
    @ManyToMany
    @JoinTable(
        name = "rendimientos_docentes",
        joinColumns = @JoinColumn(name = "id_rendimiento"),
        inverseJoinColumns = @JoinColumn(name = "id_docente")
    )
    private List<Docente> docentes;
    
    @ManyToMany
    @JoinTable(
        name = "rendimientos_representantes",
        joinColumns = @JoinColumn(name = "id_rendimiento"),
        inverseJoinColumns = @JoinColumn(name = "id_representante")
    )
    private List<Representante> representantes;
}
