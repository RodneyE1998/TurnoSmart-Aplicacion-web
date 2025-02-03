package com.uisrael.TurnoSmart.servicio.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uisrael.TurnoSmart.modelo.Cita;
import com.uisrael.TurnoSmart.modelo.Docente;
import com.uisrael.TurnoSmart.modelo.Estudiante;
import com.uisrael.TurnoSmart.modelo.HorarioDisponible;
import com.uisrael.TurnoSmart.modelo.Representante;
import com.uisrael.TurnoSmart.repositorio.CitaRepositorio;
import com.uisrael.TurnoSmart.repositorio.DocenteRepositorio;
import com.uisrael.TurnoSmart.repositorio.EstudianteRepositorio;
import com.uisrael.TurnoSmart.repositorio.HorarioDisponibleRepositorio;
import com.uisrael.TurnoSmart.servicio.CitaServicio;

/**
 * Implementación de la interfaz CitaServicio. Proporciona métodos para
 * gestionar la programación y actualización de citas.
 */
@Service
@Transactional
public class CitaServicioImpl implements CitaServicio {

	@Autowired
	private CitaRepositorio citaRepositorio;

	@Autowired
	private EstudianteRepositorio estudianteRepositorio;

	@Autowired
	private DocenteRepositorio docenteRepositorio;

	@Autowired
	private HorarioDisponibleRepositorio horarioRepositorio;

	/**
	 * Agenda una cita entre un docente y un estudiante.
	 * 
	 * @param cita         Objeto Cita con la información de la cita.
	 * @param idEstudiante ID del estudiante con el que se agenda la cita.
	 * @param idDocente    ID del docente asignado a la cita.
	 */
	@Override
	@Transactional
	public void agendarCitaPorDocente(Cita cita, Integer idEstudiante, Integer idDocente) {

		// Obtener el estudiante
		Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
				.orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

		// Obtener el representante del estudiante
		Representante representante = estudiante.getRepresentante();

		// Obtener el docente
		Docente docente = docenteRepositorio.findById(idDocente)
				.orElseThrow(() -> new RuntimeException("Docente no encontrado"));

		// Configurar la cita
		cita.setEstudiante(estudiante);
		cita.setRepresentante(representante);
		cita.setDocentes(List.of(docente));
		cita.setEstadoCita("PENDIENTE");

		// Guardar la cita
		citaRepositorio.save(cita);

	}

	@Override
	public List<Cita> obtenerCitasPorDocente(Integer idDocente) {
		return citaRepositorio.findByDocentesIdDocente(idDocente);
	}

	@Override
	@Transactional
	public void agendarCitaPorRepresentante(Integer idDocente, Integer idHorario, Representante representante,
			LocalDate fecha) {
		// Buscar el docente
		Docente docente = docenteRepositorio.findById(idDocente)
				.orElseThrow(() -> new RuntimeException("Docente no encontrado con el ID: " + idDocente));

		// Buscar el horario
		HorarioDisponible horario = horarioRepositorio.findById(idHorario)
				.orElseThrow(() -> new RuntimeException("Horario no encontrado con el ID: " + idHorario));

		// Validar si ya existe una cita para el horario seleccionado
		boolean citaExistente = citaRepositorio.existsByFechaCitaAndHoraCitaAndDocentes(fecha, horario.getHoraInicio(),
				docente);
		if (citaExistente) {
			throw new RuntimeException("El horario seleccionado ya está reservado para este docente.");
		}

		// Crear la nueva cita
		Cita nuevaCita = new Cita();
		nuevaCita.setDocentes(List.of(docente));
		nuevaCita.setEstadoCita("PENDIENTE");
		nuevaCita.setFechaCita(fecha); // Usar la fecha seleccionada por el usuario
		nuevaCita.setHoraCita(horario.getHoraInicio());
		nuevaCita.setRepresentante(representante);

		// Guardar la cita en el repositorio
		citaRepositorio.save(nuevaCita);
	}

	@Override
	public List<Cita> obtenerCitasPorRepresentante(Integer idRepresentante) {
		// TODO Auto-generated method stub
		return citaRepositorio.findByRepresentanteIdRepresentante(idRepresentante);
	}

	@Override
	@Transactional
	public void modificarCita(Integer idCita, LocalDate nuevaFecha, LocalTime nuevaHora) {
		Cita cita = citaRepositorio.findById(idCita)
				.orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
		cita.setFechaCita(nuevaFecha);
		cita.setHoraCita(nuevaHora);
		citaRepositorio.save(cita);
	}

	@Override
	@Transactional
	public void cancelarCita(Integer idCita) {
		Cita cita = citaRepositorio.findById(idCita)
				.orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + idCita));
		cita.setEstadoCita("CANCELADA");
		citaRepositorio.save(cita);
	}

	@Override
	public void confirmarCita(Integer idCita) {
		// Buscar la cita por ID
		Cita cita = citaRepositorio.findById(idCita).orElseThrow(() -> new RuntimeException("Cita no encontrada"));

		// Cambiar el estado a CONFIRMADA
		cita.setEstadoCita("CONFIRMADA");

		// Guardar los cambios en la base de datos
		citaRepositorio.save(cita);
	}

	@Override
	public Cita obtenerCitaPorId(Integer idCita) {
		// Busca la cita por ID, lanzando excepción si no se encuentra
		return citaRepositorio.findById(idCita)
				.orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + idCita));
	}

	@Override
	@Transactional
	public void marcarComoRealizada(Integer idCita) {
		// Buscar la cita por ID
		Cita cita = citaRepositorio.findById(idCita).orElseThrow(() -> new RuntimeException("Cita no encontrada"));

		// Cambiar el estado a REALIZADA
		cita.setEstadoCita("REALIZADA");

		// Guardar el cambio
		citaRepositorio.save(cita);
	}

	@Override
	public Cita obtenerCitaPorRepresentanteYFecha(Integer idRepresentante, LocalDate fecha, LocalTime hora) {
		return citaRepositorio.findByRepresentanteAndFechaCitaAndHoraCita(idRepresentante, fecha, hora).orElse(null);
	}

}
