package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class HorarioDisponible implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHorario;
    private String dia;
    /*@Temporal(TemporalType.TIME)*/
    private LocalTime horaInicio;
    /*@Temporal(TemporalType.TIME)*/
    private LocalTime horaFin;
    
    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

}
