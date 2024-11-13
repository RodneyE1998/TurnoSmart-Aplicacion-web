package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.CitaRepresentante;

public interface CitaRepresentanteServicio {
	 List<CitaRepresentante> getAllCitasRepresentantes();
	    CitaRepresentante getCitaRepresentanteById(Integer id);
	    CitaRepresentante createCitaRepresentante(CitaRepresentante citaRepresentante);
	    CitaRepresentante updateCitaRepresentante(CitaRepresentante citaRepresentante);
	    void deleteCitaRepresentante(Integer id);

}
