package com.uisrael.TurnoSmart.servicio;

public interface EmailServicio {
	
	
	 void enviarCorreo(String destinatario, String asunto, String mensaje);
	 
	 void enviarCorreoConfirmacionCita(String destinatario, String nombreRepresentante, String nombreDocente, String fecha, String hora, Integer idCita);
	 
	 void enviarCorreoConfirmacionCitaDocente(String destinatario, String nombreDocente, String nombreRepresentante, String fecha, String hora, Integer idCita);

}
