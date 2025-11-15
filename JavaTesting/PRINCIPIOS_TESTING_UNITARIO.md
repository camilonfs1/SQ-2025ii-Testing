# Principios de Testing Unitario - Análisis de los Tests

Este documento explica por qué los tests del proyecto cumplen con los principios fundamentales de testing unitario.

## Principios de Testing Unitario Cumplidos

### 1. **Aislamiento (Isolation)**

Cada test es completamente independiente y no afecta ni depende de otros tests.

**EmailControllerTest:**
- Utiliza `@Mock` para crear un mock de `IEmailService`, evitando dependencias reales
- Cada test configura sus propios mocks con `when()`
- `@BeforeEach` inicializa el estado sin depender de otros tests

**EmailServiceTest:**
- Prueba la lógica de negocio sin dependencias externas
- Cada test crea sus propios objetos `EmailRequest`

**EmailRequestTest:**
- Prueba un POJO simple sin dependencias
- Cada test es completamente independiente

### 2. **Rapidez (Fast)**

Los tests se ejecutan rápidamente porque no hay operaciones costosas.

- ✅ No hay I/O real (bases de datos, red, archivos)
- ✅ `MockMvc` simula HTTP sin levantar un servidor real
- ✅ Los mocks ejecutan instantáneamente
- ✅ No hay esperas o timeouts

### 3. **Repetibilidad (Repeatable)**

Los tests producen los mismos resultados cada vez que se ejecutan.

- ✅ Resultados determinísticos
- ✅ Sin dependencias de estado global
- ✅ Cada test configura su propio estado en `@BeforeEach`

### 4. **Autocontención (Self-contained)**

Cada test contiene toda la información necesaria para ejecutarse.

- ✅ Cada test incluye:
  - **Given/Arrange**: Preparación de datos y mocks
  - **When/Act**: Ejecución de la acción
  - **Then/Assert**: Verificación de resultados

**Ejemplo en EmailControllerTest:**
```java
// Given
when(emailService.canSendTo(anyString())).thenReturn(true);
when(emailService.sendEmail(any(EmailRequest.class))).thenReturn(true);

// When & Then
mockMvc.perform(post("/api/email/send")...)
```

### 5. **Una Sola Responsabilidad (Single Responsibility)**

Cada test verifica un escenario específico.

**EmailControllerTest:**
- `shouldSendEmailSuccessfully()` → Prueba el caso exitoso
- `shouldReturn400WhenCannotSendToRecipient()` → Prueba validación de destinatario
- `shouldReturn500WhenServiceFailsToSend()` → Prueba error del servicio
- `shouldReturn400WhenIllegalArgumentExceptionIsThrown()` → Prueba manejo de excepciones

**EmailServiceTest:**
- Tests separados para cada validación (null, vacío, formato inválido)
- Cada test valida una regla de negocio específica

### 6. **Independencia de Recursos Externos**

Los tests no dependen de recursos externos que puedan fallar o no estar disponibles.

- ✅ No hay conexiones a bases de datos
- ✅ No hay llamadas HTTP reales (`MockMvc` simula el servidor)
- ✅ No hay acceso a archivos del sistema
- ✅ El servicio de email está mockeado, no envía correos reales

### 7. **Uso de Mocks y Stubs**

Los tests utilizan mocks para aislar las dependencias.

**EmailControllerTest:**
```java
@Mock
private IEmailService emailService;

@InjectMocks
private EmailController emailController;
```

- ✅ `@Mock` crea un mock de `IEmailService`
- ✅ `@InjectMocks` inyecta el mock en el controlador
- ✅ `when().thenReturn()` define comportamientos esperados
- ✅ `verify()` confirma las interacciones

### 8. **Nombres Descriptivos**

Los tests tienen nombres claros que indican qué están probando.

- ✅ Uso de `@DisplayName` con descripciones claras
- ✅ Nombres de métodos que indican el comportamiento esperado:
  - `shouldSendEmailSuccessfully()`
  - `shouldThrowExceptionWhenToIsNull()`
  - `shouldReturn400WhenCannotSendToRecipient()`

### 9. **Cobertura de Casos**

Los tests cubren diferentes escenarios.

**Casos exitosos:**
- ✅ Envío exitoso
- ✅ Validaciones correctas

**Casos de error:**
- ✅ Valores nulos
- ✅ Valores vacíos
- ✅ Formatos inválidos
- ✅ Excepciones esperadas e inesperadas

**Casos límite:**
- ✅ Emails con subdominios
- ✅ Caracteres especiales permitidos
- ✅ Valores en blanco (solo espacios)

### 10. **Verificación de Interacciones**

Los tests verifican que las dependencias se llaman correctamente.

**EmailControllerTest** usa `verify()` para confirmar:
- ✅ Cuántas veces se llamó un método: `verify(emailService, times(1)).sendEmail(...)`
- ✅ Que no se llamó: `verify(emailService, never()).sendEmail(...)`
- ✅ Parámetros correctos: `verify(emailService, times(1)).canSendTo("test@example.com")`

### 11. **Estructura AAA (Arrange-Act-Assert)**

Los tests siguen una estructura clara y consistente.

**Ejemplo en EmailServiceTest:**
```java
@Test
void shouldThrowExceptionWhenToIsNull() {
    // Given (Arrange)
    EmailRequest request = new EmailRequest(null, "Asunto", "Cuerpo");
    
    // When & Then (Act & Assert)
    IllegalArgumentException exception = assertThrows(...);
    
    assertEquals("El destinatario es obligatorio", exception.getMessage());
}
```

## Resumen

Estos tests cumplen con los principios de testing unitario porque:

1. ✅ **Están aislados**: Cada test es independiente
2. ✅ **Son rápidos**: No hay I/O real
3. ✅ **Son repetibles**: Resultados consistentes
4. ✅ **Son autocontenidos**: Incluyen toda la configuración necesaria
5. ✅ **Tienen una sola responsabilidad**: Cada test verifica un escenario
6. ✅ **No dependen de recursos externos**: Usan mocks
7. ✅ **Usan mocks correctamente**: `@Mock`, `when()`, `verify()`
8. ✅ **Tienen nombres descriptivos**: Claros y específicos
9. ✅ **Cubren casos exitosos, errores y límites**: Cobertura completa
10. ✅ **Verifican interacciones**: Confirman llamadas a métodos

Esto facilita el mantenimiento, la depuración y la confianza en el código.

