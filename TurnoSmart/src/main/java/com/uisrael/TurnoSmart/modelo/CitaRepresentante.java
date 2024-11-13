package com.uisrael.TurnoSmart.modelo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CitaRepresentante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCitaRepresentante;
    private String estadoCita; // pendiente, cancelada, realizada
    
    @ManyToOne
    @JoinColumn(name = "id_cita", referencedColumnName = "idCita")
    private Cita cita;
    
    @ManyToOne
    @JoinColumn(name = "id_representante", referencedColumnName = "idRepresentante")
    private Representante representante;

}
