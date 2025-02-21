package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
    @Column(name = "hora_inicio") 
    private LocalTime horaInicio;
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    
    @ManyToOne
    @JoinColumn(name = "id_docente")
    @JsonIgnore
    private Docente docente;

}
