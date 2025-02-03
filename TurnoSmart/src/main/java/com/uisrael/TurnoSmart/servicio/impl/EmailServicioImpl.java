package com.uisrael.TurnoSmart.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.uisrael.TurnoSmart.servicio.EmailServicio;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailServicioImpl implements EmailServicio {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void enviarCorreo(String destinatario, String asunto, String mensaje) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(destinatario);
			helper.setSubject(asunto);
			helper.setFrom("Colegio Antonio Flores <escobarodney@gmail.com>");
			helper.setText(mensaje, true); // Permite contenido HTML

			mailSender.send(mimeMessage);
			System.out.println("Correo enviado correctamente a: " + destinatario);
		} catch (MessagingException e) {
			System.err.println("Error al enviar el correo: " + e.getMessage());
		}
	}
	
	public void enviarCorreoConfirmacionCita(String destinatario, String nombreRepresentante, String nombreDocente, String fecha, String hora, Integer idCita) {
        try {
            String urlConfirmacion = "http://localhost:8081/docente/confirmar-cita/" + idCita;

            // Contenido en HTML con el botón de confirmación
            String mensaje = "<html><body>"
                    + "<h2>Confirmación de Cita</h2>"
                    + "<p>Estimado/a <strong>" + nombreRepresentante + "</strong>,</p>"
                    + "<p>Se ha agendado una cita con el/la docente <strong>" + nombreDocente + "</strong>.</p>"
                    + "<p><strong>Fecha:</strong> " + fecha + "<br>"
                    + "<strong>Hora:</strong> " + hora + "</p>"
                    + "<p>Para confirmar su asistencia, haga clic en el siguiente botón:</p>"
                    + "<a href='" + urlConfirmacion + "' style='display:inline-block;padding:10px 15px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;'>Confirmar Cita</a>"
                    + "<p>Si el botón no funciona, copie y pegue este enlace en su navegador:</p>"
                    + "<p><a href='" + urlConfirmacion + "'>" + urlConfirmacion + "</a></p>"
                    + "<p>Saludos,<br><strong>Colegio Antonio Flores</strong></p>"
                    + "</body></html>";

            enviarCorreo(destinatario, "Nueva Cita Agendada CEIAF", mensaje);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo de confirmación: " + e.getMessage());
        }
    }
	
	public void enviarCorreoConfirmacionCitaDocente(String destinatario, String nombreDocente, String nombreRepresentante, String fecha, String hora, Integer idCita) {
	    try {
	        String urlConfirmacion = "http://localhost:8081/representante/confirmar-cita/" + idCita;

	        // Contenido en HTML con el botón de confirmación
	        String mensaje = "<html><body>"
	                + "<h2>Confirmación de Cita</h2>"
	                + "<p>Estimado/a <strong>" + nombreDocente + "</strong>,</p>"
	                + "<p>El representante <strong>" + nombreRepresentante + "</strong> ha agendado una cita con usted.</p>"
	                + "<p><strong>Fecha:</strong> " + fecha + "<br>"
	                + "<strong>Hora:</strong> " + hora + "</p>"
	                + "<p>Para confirmar su asistencia, haga clic en el siguiente botón:</p>"
	                + "<a href='" + urlConfirmacion + "' style='display:inline-block;padding:10px 15px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;'>Confirmar Cita</a>"
	                + "<p>Si el botón no funciona, copie y pegue este enlace en su navegador:</p>"
	                + "<p><a href='" + urlConfirmacion + "'>" + urlConfirmacion + "</a></p>"
	                + "<p>Saludos,<br><strong>Colegio Antonio Flores</strong></p>"
	                + "</body></html>";

	        enviarCorreo(destinatario, "Nueva Cita Agendada por Representante", mensaje);
	    } catch (Exception e) {
	        System.err.println("Error al enviar el correo de confirmación al docente: " + e.getMessage());
	    }
	}



}
