package com.university.email.controller;

import com.university.email.model.EmailRequest;
import com.university.email.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para el envío de correos electrónicos
 */
@RestController
@RequestMapping("/api/email")
@Validated
public class EmailController {
    
    private final IEmailService emailService;
    
    @Autowired
    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }
    
    /**
     * Endpoint para enviar un correo electrónico
     * 
     * @param request Datos del correo a enviar
     * @return Respuesta con el resultado del envío
     */
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendEmail(@Valid @RequestBody EmailRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Verificar si se puede enviar al destinatario
            if (!emailService.canSendTo(request.getTo())) {
                response.put("success", false);
                response.put("message", "No se puede enviar correo a este destinatario");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Intentar enviar el correo
            boolean sent = emailService.sendEmail(request);
            
            if (sent) {
                response.put("success", true);
                response.put("message", "Correo enviado exitosamente");
                response.put("to", request.getTo());
                response.put("subject", request.getSubject());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Error al enviar el correo");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Endpoint de salud para verificar que la API está funcionando
     * 
     * @return Respuesta de estado
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "Email API");
        return ResponseEntity.ok(response);
    }
}

