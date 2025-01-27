package com.uisrael.TurnoSmart.servicio;

import java.util.List;

import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.modelo.Representante;

public interface RepresentanteServicio {
	
	List<Representante> getAllRepresentantes();
    
	Representante findByIdRepresentante(Integer idRepresentante);
    
    Representante createRepresentante(Representante representante);
    
    Representante updateRepresentante(Representante representante);
    
    void deleteRepresentante(Integer idRepresentante);
    
    Representante obtenerPorUsuario(String username);
    
    List<Docente> obtenerDocentesAsociados(String username);

}
