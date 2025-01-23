package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.HorarioDisponible;

public interface HorarioDisponibleServicio {
	
	HorarioDisponible guardarHorario(HorarioDisponible horario);
	
    void eliminarHorario(Integer idHorario);
    
    List<HorarioDisponible> obtenerHorariosPorDocente(Integer idDocente);
    
    List<HorarioDisponible> obtenerTodosLosHorarios();
    
    HorarioDisponible obtenerPorId(Integer idHorario);
	
}
