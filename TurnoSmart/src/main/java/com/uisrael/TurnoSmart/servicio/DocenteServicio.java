package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.Docente;

public interface DocenteServicio {
	List<Docente> getAllDocentes();
    Docente getDocenteByNumeroIdentificacion(String numeroIdentificacion);
    Docente createDocente(Docente docente);
    Docente updateDocente(Docente docente);
    void deleteDocente(String numeroIdentificacion);
    
    List<Docente> obtenerDocentesPorEstudiante(Integer idEstudiante);

}
