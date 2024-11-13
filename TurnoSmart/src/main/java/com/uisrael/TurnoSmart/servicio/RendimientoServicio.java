package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.Rendimiento;

public interface RendimientoServicio {
	List<Rendimiento> getAllRendimientos();
    Rendimiento getRendimientoById(Integer id);
    Rendimiento createRendimiento(Rendimiento rendimiento);
    Rendimiento updateRendimiento(Rendimiento rendimiento);
    void deleteRendimiento(Integer id);

}
