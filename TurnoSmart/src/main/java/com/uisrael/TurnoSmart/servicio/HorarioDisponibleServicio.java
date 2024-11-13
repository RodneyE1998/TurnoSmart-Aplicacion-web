package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.HorarioDisponible;

public interface HorarioDisponibleServicio {
	List<HorarioDisponible> getAllHorariosDisponibles();
    HorarioDisponible getHorarioDisponibleById(Integer id);
    HorarioDisponible createHorarioDisponible(HorarioDisponible horarioDisponible);
    HorarioDisponible updateHorarioDisponible(HorarioDisponible horarioDisponible);
    void deleteHorarioDisponible(Integer id);

}
