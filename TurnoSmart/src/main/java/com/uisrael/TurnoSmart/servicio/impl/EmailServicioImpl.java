package com.uisrael.TurnoSmart.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.uisrael.TurnoSmart.servicio.EmailServicio;
import org.springframework.mail.SimpleMailMessage;


@Service
public class EmailServicioImpl implements EmailServicio {
	
	@Autowired
    private JavaMailSender mailSender;

	@Override
	public void enviarCorreo(String destinatario, String asunto, String mensaje) {
	    try {
	        SimpleMailMessage email = new SimpleMailMessage();
	        email.setTo(destinatario);
	        email.setSubject(asunto);
	        email.setText(mensaje);
	        email.setFrom("Colegio Antonio Flores <escobarodney@gmail.com>"); // Personalizar remitente aquí
	        mailSender.send(email);
	    } catch (Exception e) {
	        System.err.println("Error al enviar el correo: " + e.getMessage());
	    }
	}


}
