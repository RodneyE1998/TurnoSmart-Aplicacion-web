package com.uisrael.TurnoSmart.servicio;

import java.time.LocalDate;
import java.util.List;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Representante;

public interface CitaServicio {
	
	public List<Cita> getAllCitas();
	
	Cita getCitaById(Integer id);
    
	Cita createCita(Cita cita);
    
	Cita updateCita(Cita cita);
    
	void deleteCita(Integer id);
	
	void agendarCitaPorDocente(Cita cita, Integer idEstudiante, Integer idDocente);
	
	List<Cita> obtenerCitasPorDocente(Integer idDocente);
	
	void agendarCitaPorRepresentante(Integer idDocente, Integer idHorario, Representante representante, LocalDate fecha);
	
	List<Cita> obtenerCitasPorRepresentante(Integer idRepresentante);
	

}
