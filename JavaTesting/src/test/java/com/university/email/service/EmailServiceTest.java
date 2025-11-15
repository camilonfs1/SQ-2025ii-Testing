package com.university.email.service;

import com.university.email.model.EmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para EmailService
 */
@DisplayName("EmailService Tests")
class EmailServiceTest {

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
    }

    // ========== Tests para sendEmail() ==========

    @Test
    @DisplayName("Debería lanzar excepción cuando la solicitud es nula")
    void shouldThrowExceptionWhenRequestIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(null)
        );

        assertEquals("La solicitud de correo no puede ser nula", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el destinatario es nulo")
    void shouldThrowExceptionWhenToIsNull() {
        // Given
        EmailRequest request = new EmailRequest(null, "Asunto", "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El destinatario es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el destinatario está vacío")
    void shouldThrowExceptionWhenToIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest("", "Asunto", "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El destinatario es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el destinatario solo tiene espacios")
    void shouldThrowExceptionWhenToIsBlank() {
        // Given
        EmailRequest request = new EmailRequest("   ", "Asunto", "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El destinatario es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el asunto es nulo")
    void shouldThrowExceptionWhenSubjectIsNull() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", null, "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El asunto es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el asunto está vacío")
    void shouldThrowExceptionWhenSubjectIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "", "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El asunto es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el cuerpo es nulo")
    void shouldThrowExceptionWhenBodyIsNull() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El cuerpo del mensaje es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el cuerpo está vacío")
    void shouldThrowExceptionWhenBodyIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El cuerpo del mensaje es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el formato de email es inválido")
    void shouldThrowExceptionWhenEmailFormatIsInvalid() {
        // Given
        EmailRequest request = new EmailRequest("invalid-email", "Asunto", "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El formato del correo destinatario no es válido", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el email no tiene @")
    void shouldThrowExceptionWhenEmailHasNoAtSymbol() {
        // Given
        EmailRequest request = new EmailRequest("invalidemail.com", "Asunto", "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El formato del correo destinatario no es válido", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el email no tiene dominio")
    void shouldThrowExceptionWhenEmailHasNoDomain() {
        // Given
        EmailRequest request = new EmailRequest("test@", "Asunto", "Cuerpo");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> emailService.sendEmail(request)
        );

        assertEquals("El formato del correo destinatario no es válido", exception.getMessage());
    }

    @Test
    @DisplayName("Debería retornar false cuando el email contiene 'error@'")
    void shouldReturnFalseWhenEmailContainsError() {
        // Given
        EmailRequest request = new EmailRequest("error@example.com", "Asunto", "Cuerpo");

        // When
        boolean result = emailService.sendEmail(request);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería retornar true cuando el correo se envía exitosamente")
    void shouldReturnTrueWhenEmailIsSentSuccessfully() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");

        // When
        boolean result = emailService.sendEmail(request);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Debería usar el email from por defecto cuando from es nulo")
    void shouldUseDefaultFromWhenFromIsNull() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");
        request.setFrom(null);

        // When
        boolean result = emailService.sendEmail(request);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Debería usar el email from por defecto cuando from está vacío")
    void shouldUseDefaultFromWhenFromIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");
        request.setFrom("");

        // When
        boolean result = emailService.sendEmail(request);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Debería usar el email from personalizado cuando se proporciona")
    void shouldUseCustomFromWhenProvided() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo", "custom@university.edu");

        // When
        boolean result = emailService.sendEmail(request);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Debería aceptar emails con subdominios")
    void shouldAcceptEmailsWithSubdomains() {
        // Given
        EmailRequest request = new EmailRequest("test@mail.example.com", "Asunto", "Cuerpo");

        // When
        boolean result = emailService.sendEmail(request);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Debería aceptar emails con caracteres especiales permitidos")
    void shouldAcceptEmailsWithAllowedSpecialCharacters() {
        // Given
        EmailRequest request = new EmailRequest("test.user+tag@example.co.uk", "Asunto", "Cuerpo");

        // When
        boolean result = emailService.sendEmail(request);

        // Then
        assertTrue(result);
    }

    // ========== Tests para canSendTo() ==========

    @Test
    @DisplayName("Debería retornar false cuando el email es nulo")
    void shouldReturnFalseWhenEmailIsNull() {
        // When
        boolean result = emailService.canSendTo(null);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería retornar false cuando el email está vacío")
    void shouldReturnFalseWhenEmailIsEmpty() {
        // When
        boolean result = emailService.canSendTo("");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería retornar false cuando el email solo tiene espacios")
    void shouldReturnFalseWhenEmailIsBlank() {
        // When
        boolean result = emailService.canSendTo("   ");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería retornar false cuando el email contiene 'blocked'")
    void shouldReturnFalseWhenEmailContainsBlocked() {
        // When
        boolean result = emailService.canSendTo("blocked@example.com");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería retornar false cuando el email contiene 'blocked' en cualquier parte")
    void shouldReturnFalseWhenEmailContainsBlockedAnywhere() {
        // When
        boolean result = emailService.canSendTo("user@blocked.example.com");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería retornar true cuando el email es válido y no contiene 'blocked'")
    void shouldReturnTrueWhenEmailIsValidAndNotBlocked() {
        // When
        boolean result = emailService.canSendTo("test@example.com");

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Debería retornar true para emails con diferentes formatos válidos")
    void shouldReturnTrueForDifferentValidEmailFormats() {
        // When & Then
        assertTrue(emailService.canSendTo("user@domain.com"));
        assertTrue(emailService.canSendTo("user.name@domain.co.uk"));
        assertTrue(emailService.canSendTo("user+tag@example.org"));
    }
}

