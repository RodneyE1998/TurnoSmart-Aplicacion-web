package com.uisrael.TurnoSmart.controlador;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.TurnoSmart.modelo.HorarioDisponible;
import com.uisrael.TurnoSmart.servicio.HorarioDisponibleServicio;

@RestController
@RequestMapping("/api/horarios-disponibles")
public class HistoriaDisponibleControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private HorarioDisponibleServicio horarioDisponibleService;

    @GetMapping
    public List<HorarioDisponible> getAllHorariosDisponibles() {
        return horarioDisponibleService.getAllHorariosDisponibles();
    }

    @GetMapping("/{id}")
    public HorarioDisponible getHorarioDisponibleById(@PathVariable Integer id) {
        return horarioDisponibleService.getHorarioDisponibleById(id);
    }

    @PostMapping
    public HorarioDisponible createHorarioDisponible(@RequestBody HorarioDisponible horarioDisponible) {
        return horarioDisponibleService.createHorarioDisponible(horarioDisponible);
    }
    
    @PutMapping("/{id}")
    public HorarioDisponible updateHorarioDisponible(@PathVariable Integer id, @RequestBody HorarioDisponible horarioDisponible) {
        return horarioDisponibleService.updateHorarioDisponible(horarioDisponible);
    }

    @DeleteMapping("/{id}")
    public void deleteHorarioDisponible(@PathVariable Integer id) {
        horarioDisponibleService.deleteHorarioDisponible(id);
    }

}
