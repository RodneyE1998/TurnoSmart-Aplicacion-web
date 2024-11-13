package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.Cita;

public interface CitaServicio {
	
	public List<Cita> getAllCitas();
	Cita getCitaById(Integer id);
    Cita createCita(Cita cita);
    Cita updateCita(Cita cita);
    void deleteCita(Integer id);
	

}
