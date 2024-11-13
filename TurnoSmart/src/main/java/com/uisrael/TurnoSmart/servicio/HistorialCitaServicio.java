package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.HistorialCita;

public interface HistorialCitaServicio {
	List<HistorialCita> getAllHistorialCitas();
    HistorialCita getHistorialCitaById(Integer id);
    HistorialCita createHistorialCita(HistorialCita historialCita);
    HistorialCita updateHistorialCita(HistorialCita historialCita);
    void deleteHistorialCita(Integer id);

}
