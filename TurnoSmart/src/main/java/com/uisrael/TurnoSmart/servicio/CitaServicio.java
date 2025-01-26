package com.uisrael.TurnoSmart.servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Representante;

public interface CitaServicio {
	
	void agendarCitaPorDocente(Cita cita, Integer idEstudiante, Integer idDocente);
	
	List<Cita> obtenerCitasPorDocente(Integer idDocente);
	
	void agendarCitaPorRepresentante(Integer idDocente, Integer idHorario, Representante representante, LocalDate fecha);
	
	List<Cita> obtenerCitasPorRepresentante(Integer idRepresentante);
	
    void modificarCita(Integer idCita, LocalDate nuevaFecha, LocalTime nuevaHora);
    
    void cancelarCita(Integer idCita);
    
    void confirmarCita(Integer idCita);
    
    Cita obtenerCitaPorId(Integer idCita);
    
    void marcarComoRealizada(Integer idCita);


}
