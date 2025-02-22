package com.uisrael.TurnoSmart.servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Cita.TipoCita;
import com.uisrael.TurnoSmart.modelo.Representante;

public interface CitaServicio {
	
	void agendarCitaPorDocente(Cita cita, Integer idEstudiante, Integer idDocente, String motivoCita, Cita.TipoCita tipoCita);
	
	List<Cita> obtenerCitasPorDocente(Integer idDocente);
	
	void agendarCitaPorRepresentante(Integer idDocente, Integer idHorario, Representante representante, 
            LocalDate fecha, String motivoCita, TipoCita tipoCita);

	
	List<Cita> obtenerCitasPorRepresentante(Integer idRepresentante);
	
    void modificarCita(Integer idCita, LocalDate nuevaFecha, LocalTime nuevaHora);
    
    void cancelarCita(Integer idCita);
    
    void confirmarCita(Integer idCita);
    
    Cita obtenerCitaPorId(Integer idCita);
    
    void marcarComoRealizada(Integer idCita);
    
    Cita obtenerCitaPorRepresentanteYFecha(Integer idRepresentante, LocalDate fecha, LocalTime hora);


}
