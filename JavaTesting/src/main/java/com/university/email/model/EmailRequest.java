package com.university.email.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Modelo para recibir los datos del correo electrónico
 */
public class EmailRequest {
    
    @NotBlank(message = "El destinatario es obligatorio")
    @Email(message = "El destinatario debe ser un correo electrónico válido")
    private String to;
    
    @NotBlank(message = "El asunto es obligatorio")
    private String subject;
    
    @NotBlank(message = "El cuerpo del mensaje es obligatorio")
    private String body;
    
    private String from;

    public EmailRequest() {
    }

    public EmailRequest(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public EmailRequest(String to, String subject, String body, String from) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}

