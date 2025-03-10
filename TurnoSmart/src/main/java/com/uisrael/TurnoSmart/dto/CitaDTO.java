package com.uisrael.TurnoSmart.dto;

public class CitaDTO {

	private int idCita;
	private String fechaCita;
	private String horaCita;
	private String docenteNombre;
	private String estadoCita;
	private String motivoCita;

	// Constructor corregido
	public CitaDTO(int idCita, String fechaCita, String horaCita, String docenteNombre, String estadoCita,
			String motivoCita) {
		this.idCita = idCita;
		this.fechaCita = fechaCita;
		this.horaCita = horaCita;
		this.docenteNombre = docenteNombre;
		this.estadoCita = estadoCita;
		this.motivoCita = motivoCita; 
	}

	// Getters y Setters
	public int getIdCita() {
		return idCita;
	}

	public void setIdCita(int idCita) {
		this.idCita = idCita;
	}

	public String getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(String fechaCita) {
		this.fechaCita = fechaCita;
	}

	public String getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}

	public String getDocenteNombre() {
		return docenteNombre;
	}

	public void setDocenteNombre(String docenteNombre) {
		this.docenteNombre = docenteNombre;
	}

	public String getEstadoCita() {
		return estadoCita;
	}

	public void setEstadoCita(String estadoCita) {
		this.estadoCita = estadoCita;
	}

	public String getMotivoCita() {
		return motivoCita;
	}

	public void setMotivoCita(String motivoCita) {
		this.motivoCita = motivoCita;
	}

}
