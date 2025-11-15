package com.university.email.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para EmailRequest
 */
@DisplayName("EmailRequest Tests")
class EmailRequestTest {

    private final Validator validator;

    public EmailRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    // ========== Tests para constructores ==========

    @Test
    @DisplayName("Debería crear EmailRequest con constructor sin parámetros")
    void shouldCreateEmailRequestWithNoArgsConstructor() {
        // When
        EmailRequest request = new EmailRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getTo());
        assertNull(request.getSubject());
        assertNull(request.getBody());
        assertNull(request.getFrom());
    }

    @Test
    @DisplayName("Debería crear EmailRequest con constructor de 3 parámetros")
    void shouldCreateEmailRequestWithThreeParameterConstructor() {
        // When
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");

        // Then
        assertNotNull(request);
        assertEquals("test@example.com", request.getTo());
        assertEquals("Asunto", request.getSubject());
        assertEquals("Cuerpo", request.getBody());
        assertNull(request.getFrom());
    }

    @Test
    @DisplayName("Debería crear EmailRequest con constructor de 4 parámetros")
    void shouldCreateEmailRequestWithFourParameterConstructor() {
        // When
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo", "from@example.com");

        // Then
        assertNotNull(request);
        assertEquals("test@example.com", request.getTo());
        assertEquals("Asunto", request.getSubject());
        assertEquals("Cuerpo", request.getBody());
        assertEquals("from@example.com", request.getFrom());
    }

    // ========== Tests para getters y setters ==========

    @Test
    @DisplayName("Debería establecer y obtener el campo 'to' correctamente")
    void shouldSetAndGetToCorrectly() {
        // Given
        EmailRequest request = new EmailRequest();
        String email = "test@example.com";

        // When
        request.setTo(email);

        // Then
        assertEquals(email, request.getTo());
    }

    @Test
    @DisplayName("Debería establecer y obtener el campo 'subject' correctamente")
    void shouldSetAndGetSubjectCorrectly() {
        // Given
        EmailRequest request = new EmailRequest();
        String subject = "Test Subject";

        // When
        request.setSubject(subject);

        // Then
        assertEquals(subject, request.getSubject());
    }

    @Test
    @DisplayName("Debería establecer y obtener el campo 'body' correctamente")
    void shouldSetAndGetBodyCorrectly() {
        // Given
        EmailRequest request = new EmailRequest();
        String body = "Test Body";

        // When
        request.setBody(body);

        // Then
        assertEquals(body, request.getBody());
    }

    @Test
    @DisplayName("Debería establecer y obtener el campo 'from' correctamente")
    void shouldSetAndGetFromCorrectly() {
        // Given
        EmailRequest request = new EmailRequest();
        String from = "from@example.com";

        // When
        request.setFrom(from);

        // Then
        assertEquals(from, request.getFrom());
    }

    @Test
    @DisplayName("Debería permitir establecer 'from' como nulo")
    void shouldAllowSettingFromAsNull() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo", "from@example.com");

        // When
        request.setFrom(null);

        // Then
        assertNull(request.getFrom());
    }

    // ========== Tests para toString() ==========

    @Test
    @DisplayName("Debería retornar representación en string correcta")
    void shouldReturnCorrectStringRepresentation() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo", "from@example.com");

        // When
        String result = request.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("test@example.com"));
        assertTrue(result.contains("Asunto"));
        assertTrue(result.contains("Cuerpo"));
        assertTrue(result.contains("from@example.com"));
        assertTrue(result.startsWith("EmailRequest{"));
    }

    @Test
    @DisplayName("Debería retornar representación en string con from nulo")
    void shouldReturnCorrectStringRepresentationWithNullFrom() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");

        // When
        String result = request.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("test@example.com"));
        assertTrue(result.contains("Asunto"));
        assertTrue(result.contains("Cuerpo"));
        assertTrue(result.contains("from='null'") || result.contains("from=null"));
    }

    // ========== Tests para validaciones Bean Validation ==========

    @Test
    @DisplayName("Debería pasar validación cuando todos los campos requeridos son válidos")
    void shouldPassValidationWhenAllRequiredFieldsAreValid() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'to' es nulo")
    void shouldFailValidationWhenToIsNull() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setSubject("Asunto");
        request.setBody("Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<EmailRequest> violation = violations.iterator().next();
        assertEquals("to", violation.getPropertyPath().toString());
        assertTrue(violation.getMessage().contains("obligatorio"));
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'to' está vacío")
    void shouldFailValidationWhenToIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setTo("");
        request.setSubject("Asunto");
        request.setBody("Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        boolean hasToViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("to"));
        assertTrue(hasToViolation);
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'to' solo tiene espacios")
    void shouldFailValidationWhenToIsBlank() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setTo("   ");
        request.setSubject("Asunto");
        request.setBody("Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        boolean hasToViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("to"));
        assertTrue(hasToViolation);
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'to' no es un email válido")
    void shouldFailValidationWhenToIsNotValidEmail() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setTo("invalid-email");
        request.setSubject("Asunto");
        request.setBody("Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("to") 
                        && v.getMessage().contains("correo electrónico válido"));
        assertTrue(hasEmailViolation);
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'subject' es nulo")
    void shouldFailValidationWhenSubjectIsNull() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setTo("test@example.com");
        request.setBody("Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        boolean hasSubjectViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("subject"));
        assertTrue(hasSubjectViolation);
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'subject' está vacío")
    void shouldFailValidationWhenSubjectIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setTo("test@example.com");
        request.setSubject("");
        request.setBody("Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        boolean hasSubjectViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("subject"));
        assertTrue(hasSubjectViolation);
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'body' es nulo")
    void shouldFailValidationWhenBodyIsNull() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setTo("test@example.com");
        request.setSubject("Asunto");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        boolean hasBodyViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("body"));
        assertTrue(hasBodyViolation);
    }

    @Test
    @DisplayName("Debería fallar validación cuando 'body' está vacío")
    void shouldFailValidationWhenBodyIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest();
        request.setTo("test@example.com");
        request.setSubject("Asunto");
        request.setBody("");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        boolean hasBodyViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("body"));
        assertTrue(hasBodyViolation);
    }

    @Test
    @DisplayName("Debería pasar validación cuando 'from' es nulo (campo opcional)")
    void shouldPassValidationWhenFromIsNull() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");
        request.setFrom(null);

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería pasar validación cuando 'from' está vacío (campo opcional)")
    void shouldPassValidationWhenFromIsEmpty() {
        // Given
        EmailRequest request = new EmailRequest("test@example.com", "Asunto", "Cuerpo");
        request.setFrom("");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería pasar validación con email válido con subdominio")
    void shouldPassValidationWithValidEmailWithSubdomain() {
        // Given
        EmailRequest request = new EmailRequest("test@mail.example.com", "Asunto", "Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería pasar validación con email válido con extensión de dominio múltiple")
    void shouldPassValidationWithValidEmailWithMultipleDomainExtension() {
        // Given
        EmailRequest request = new EmailRequest("test@example.co.uk", "Asunto", "Cuerpo");

        // When
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }
}

