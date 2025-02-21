package com.uisrael.TurnoSmart.dto;

public class DocenteDTO {
	
	private Integer idDocente;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String numeroIdentificacion;

    // Constructor
    public DocenteDTO(Integer idDocente, String nombre, String apellido, String email, String telefono, String numeroIdentificacion) {
        this.idDocente = idDocente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    // Getters y Setters
    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

}
