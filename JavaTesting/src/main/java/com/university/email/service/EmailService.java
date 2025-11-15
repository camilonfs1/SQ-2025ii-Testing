package com.university.email.service;

import com.university.email.model.EmailRequest;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Servicio para el envío de correos electrónicos
 * Implementación simulada para propósitos educativos
 */
@Service
public class EmailService implements IEmailService {
    
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());
    private static final String DEFAULT_FROM = "noreply@university.edu";
    
    /**
     * Envía un correo electrónico
     * 
     * @param request Datos del correo a enviar
     * @return true si el correo se envió exitosamente, false en caso contrario
     * @throws IllegalArgumentException si los datos del correo son inválidos
     */
    public boolean sendEmail(EmailRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de correo no puede ser nula");
        }
        
        if (request.getTo() == null || request.getTo().trim().isEmpty()) {
            throw new IllegalArgumentException("El destinatario es obligatorio");
        }
        
        if (request.getSubject() == null || request.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("El asunto es obligatorio");
        }
        
        if (request.getBody() == null || request.getBody().trim().isEmpty()) {
            throw new IllegalArgumentException("El cuerpo del mensaje es obligatorio");
        }
        
        // Validar formato de email
        if (!isValidEmail(request.getTo())) {
            throw new IllegalArgumentException("El formato del correo destinatario no es válido");
        }
        
        // Simulación de envío de correo
        String fromEmail = request.getFrom() != null && !request.getFrom().trim().isEmpty() 
            ? request.getFrom() 
            : DEFAULT_FROM;
        
        logger.info(String.format("Enviando correo de %s a %s con asunto: %s", 
            fromEmail, request.getTo(), request.getSubject()));
        
        // Simulación de posibles errores
        if (request.getTo().contains("error@")) {
            logger.warning("Error simulado: No se pudo enviar el correo");
            return false;
        }
        
        // Simulación de éxito
        logger.info("Correo enviado exitosamente");
        return true;
    }
    
    /**
     * Valida el formato de un correo electrónico
     * 
     * @param email Correo a validar
     * @return true si el formato es válido, false en caso contrario
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Validación simple de formato de email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Verifica si un correo puede ser enviado (simulación de verificación)
     * 
     * @param email Correo a verificar
     * @return true si el correo puede recibir mensajes, false en caso contrario
     */
    public boolean canSendTo(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Simulación: correos que contienen "blocked" no pueden recibir mensajes
        return !email.contains("blocked");
    }
}

