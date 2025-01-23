package com.uisrael.TurnoSmart.controlador;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.uisrael.TurnoSmart.modelo.HorarioDisponible;
import com.uisrael.TurnoSmart.modelo.Usuario;
import com.uisrael.TurnoSmart.repositorio.UsuarioRepositorio;
import com.uisrael.TurnoSmart.servicio.HorarioDisponibleServicio;

@Controller
@RequestMapping("/docente/horarios")
public class HistoriaDisponibleControlador {

	
	@Autowired
    private HorarioDisponibleServicio horarioServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @GetMapping
    public String mostrarHorarios(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Integer idDocente = usuario.getDocente().getIdDocente();
        List<HorarioDisponible> horarios = horarioServicio.obtenerHorariosPorDocente(idDocente);

        model.addAttribute("horarios", horarios);
        return "GestionHorariosDocente";
    }
    
    @PostMapping("/guardar")
    public String guardarHorario(@ModelAttribute HorarioDisponible horario, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        horario.setDocente(usuario.getDocente());
        horarioServicio.guardarHorario(horario);

        return "redirect:/docente/horarios";
    }
    
    @PostMapping("/eliminar")
    public String eliminarHorario(@RequestParam("idHorario") Integer idHorario) {
        horarioServicio.eliminarHorario(idHorario);
        return "redirect:/docente/horarios";
    }
    


}
