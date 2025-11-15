package com.university.email.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.email.model.EmailRequest;
import com.university.email.service.IEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias para EmailController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("EmailController Tests")
class EmailControllerTest {

    @Mock
    private IEmailService emailService;

    @InjectMocks
    private EmailController emailController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Debería enviar correo exitosamente cuando todos los datos son válidos")
    void shouldSendEmailSuccessfully() throws Exception {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Test Subject", "Test Body");
        when(emailService.canSendTo(anyString())).thenReturn(true);
        when(emailService.sendEmail(any(EmailRequest.class))).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Correo enviado exitosamente"))
                .andExpect(jsonPath("$.to").value("test@example.com"))
                .andExpect(jsonPath("$.subject").value("Test Subject"));

        verify(emailService, times(1)).canSendTo("test@example.com");
        verify(emailService, times(1)).sendEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("Debería retornar 400 cuando no se puede enviar al destinatario")
    void shouldReturn400WhenCannotSendToRecipient() throws Exception {
        // Given
        EmailRequest request = new EmailRequest("blocked@example.com", "Test Subject", "Test Body");
        when(emailService.canSendTo(anyString())).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("No se puede enviar correo a este destinatario"));

        verify(emailService, times(1)).canSendTo("blocked@example.com");
        verify(emailService, never()).sendEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("Debería retornar 500 cuando el servicio falla al enviar")
    void shouldReturn500WhenServiceFailsToSend() throws Exception {
        // Given
        EmailRequest request = new EmailRequest("error@example.com", "Test Subject", "Test Body");
        when(emailService.canSendTo(anyString())).thenReturn(true);
        when(emailService.sendEmail(any(EmailRequest.class))).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error al enviar el correo"));

        verify(emailService, times(1)).canSendTo("error@example.com");
        verify(emailService, times(1)).sendEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("Debería retornar 400 cuando se lanza IllegalArgumentException")
    void shouldReturn400WhenIllegalArgumentExceptionIsThrown() throws Exception {
        // Given
        // Usamos un email válido en formato para que pase la validación de Spring
        // pero el servicio lanzará la excepción
        EmailRequest request = new EmailRequest("test@example.com", "Test Subject", "Test Body");
        when(emailService.canSendTo(anyString())).thenReturn(true);
        when(emailService.sendEmail(any(EmailRequest.class)))
                .thenThrow(new IllegalArgumentException("El formato del correo destinatario no es válido"));

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El formato del correo destinatario no es válido"));

        verify(emailService, times(1)).canSendTo("test@example.com");
        verify(emailService, times(1)).sendEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("Debería retornar 500 cuando se lanza una excepción inesperada")
    void shouldReturn500WhenUnexpectedExceptionIsThrown() throws Exception {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Test Subject", "Test Body");
        when(emailService.canSendTo(anyString())).thenReturn(true);
        when(emailService.sendEmail(any(EmailRequest.class)))
                .thenThrow(new RuntimeException("Error inesperado"));

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error inesperado: Error inesperado"));

        verify(emailService, times(1)).canSendTo("test@example.com");
        verify(emailService, times(1)).sendEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("Debería retornar 200 OK en el endpoint de health")
    void shouldReturn200OnHealthEndpoint() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/email/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.service").value("Email API"));

        verify(emailService, never()).canSendTo(anyString());
        verify(emailService, never()).sendEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("Debería enviar correo exitosamente con campo from personalizado")
    void shouldSendEmailSuccessfullyWithCustomFrom() throws Exception {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Test Subject", "Test Body", "custom@university.edu");
        when(emailService.canSendTo(anyString())).thenReturn(true);
        when(emailService.sendEmail(any(EmailRequest.class))).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Correo enviado exitosamente"));

        verify(emailService, times(1)).canSendTo("test@example.com");
        verify(emailService, times(1)).sendEmail(any(EmailRequest.class));
    }
}

