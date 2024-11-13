package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class HistorialCita implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorial;
	//@Temporal(TemporalType.DATE)
    private LocalDate fechaRegistro;
    @Enumerated(EnumType.STRING) // Almacena como cadena
    private EstadoCita estadoCita; // Cambiado a Enum
    
    public enum EstadoCita {
        PENDIENTE,
        CANCELADA,
        REALIZADA
    }
    
    @ManyToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

}
