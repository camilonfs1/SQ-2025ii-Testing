package com.university.email.service;

import com.university.email.model.EmailRequest;

/**
 * Interfaz para el servicio de envío de correos electrónicos
 */
public interface IEmailService {
    
    /**
     * Envía un correo electrónico
     * 
     * @param request Datos del correo a enviar
     * @return true si el correo se envió exitosamente, false en caso contrario
     * @throws IllegalArgumentException si los datos del correo son inválidos
     */
    boolean sendEmail(EmailRequest request);
    
    /**
     * Verifica si un correo puede ser enviado
     * 
     * @param email Correo a verificar
     * @return true si el correo puede recibir mensajes, false en caso contrario
     */
    boolean canSendTo(String email);
}

