# API de Envío de Correos Electrónicos

API pequeña desarrollada en Java para el envío de correos electrónicos, con pruebas unitarias completas. Proyecto educativo para clases de testing.

## 📋 Descripción

Esta API REST permite enviar correos electrónicos mediante un endpoint HTTP. La implementación es simulada (no envía correos reales) y está diseñada específicamente para propósitos educativos, enfocándose en las pruebas unitarias.

## 🏗️ Estructura del Proyecto

```
JavaTesting/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/university/email/
│   │           ├── EmailApiApplication.java    # Clase principal
│   │           ├── controller/
│   │           │   └── EmailController.java   # Controlador REST
│   │           ├── service/
│   │           │   ├── IEmailService.java    # Interfaz del servicio
│   │           │   └── EmailService.java     # Implementación del servicio
│   │           └── model/
│   │               └── EmailRequest.java      # Modelo de datos
│   └── test/
│       └── java/
│           └── com/university/email/
│               ├── controller/
│               │   └── EmailControllerTest.java
│               ├── service/
│               │   └── EmailServiceTest.java
│               └── model/
│                   └── EmailRequestTest.java
├── pom.xml                                    # Configuración Maven
└── README.md
```

## 🚀 Requisitos

- Java 11 o superior
- Maven 3.6 o superior

## 📦 Instalación

1. Clonar o descargar el proyecto
2. Navegar al directorio del proyecto:
   ```bash
   cd JavaTesting
   ```
3. Compilar el proyecto:
   ```bash
   mvn clean compile
   ```

## 🧪 Ejecutar Pruebas

Para ejecutar todas las pruebas unitarias:

```bash
mvn test
```

Para ejecutar pruebas con más detalles:

```bash
mvn test -Dtest=EmailServiceTest
mvn test -Dtest=EmailControllerTest
mvn test -Dtest=EmailRequestTest
```

## 🏃 Ejecutar la Aplicación

Para ejecutar la aplicación Spring Boot:

```bash
mvn spring-boot:run
```

O compilar y ejecutar el JAR:

```bash
mvn clean package
java -jar target/email-api-1.0.0.jar
```

La API estará disponible en: `http://localhost:8080`

## 📡 Endpoints

### 1. Enviar Correo Electrónico

**POST** `/api/email/send`

**Request Body:**
```json
{
  "to": "destinatario@example.com",
  "subject": "Asunto del correo",
  "body": "Cuerpo del mensaje",
  "from": "remitente@example.com"  // Opcional
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "success": true,
  "message": "Correo enviado exitosamente",
  "to": "destinatario@example.com",
  "subject": "Asunto del correo"
}
```

**Respuesta de Error (400 Bad Request):**
```json
{
  "success": false,
  "message": "El destinatario es obligatorio"
}
```

### 2. Health Check

**GET** `/api/email/health`

**Respuesta (200 OK):**
```json
{
  "status": "OK",
  "service": "Email API"
}
```

## 📝 Ejemplos de Uso

### Usando cURL

```bash
# Enviar correo
curl -X POST http://localhost:8080/api/email/send \
  -H "Content-Type: application/json" \
  -d '{
    "to": "test@example.com",
    "subject": "Prueba",
    "body": "Este es un correo de prueba"
  }'

# Health check
curl http://localhost:8080/api/email/health
```

### Usando Postman

1. Crear una nueva petición POST
2. URL: `http://localhost:8080/api/email/send`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
   ```json
   {
     "to": "test@example.com",
     "subject": "Prueba",
     "body": "Este es un correo de prueba"
   }
   ```

## 🧪 Cobertura de Pruebas

El proyecto incluye pruebas unitarias completas que cubren:

### EmailServiceTest
- ✅ Envío exitoso de correos
- ✅ Validación de campos obligatorios
- ✅ Validación de formato de email
- ✅ Manejo de errores simulados
- ✅ Uso de remitente por defecto
- ✅ Validación de destinatarios bloqueados

### EmailControllerTest
- ✅ Envío exitoso (200 OK)
- ✅ Errores de validación (400 Bad Request)
- ✅ Errores del servidor (500 Internal Server Error)
- ✅ Manejo de excepciones
- ✅ Health check endpoint
- ✅ Validación de JSON mal formado

### EmailRequestTest
- ✅ Constructores
- ✅ Getters y Setters
- ✅ Manejo de valores nulos
- ✅ Representación en string

## 🔍 Características de Testing

- **JUnit 5**: Framework de pruebas
- **Mockito**: Para mocking de dependencias
- **MockMvc**: Para pruebas de controladores REST
- **@DisplayName**: Nombres descriptivos para las pruebas
- **Cobertura completa**: Pruebas para casos exitosos y de error

## 📚 Conceptos de Testing Aplicados

1. **Arrange-Act-Assert (AAA)**: Patrón usado en todas las pruebas
2. **Mocking**: Uso de Mockito para aislar dependencias
3. **Test Coverage**: Cobertura de casos normales y excepcionales
4. **Unit Testing**: Pruebas aisladas de cada componente
5. **Integration Testing**: Pruebas del controlador con MockMvc

## ⚠️ Notas Importantes

- Esta es una implementación **simulada** para propósitos educativos
- No se envían correos electrónicos reales
- El servicio simula errores cuando el correo contiene "error@"
- Los correos que contienen "blocked" son rechazados automáticamente
- El remitente por defecto es `noreply@university.edu` si no se especifica

## 🛠️ Tecnologías Utilizadas

- **Java 11**
- **Spring Boot 2.7.14**
- **Maven**
- **JUnit 5**
- **Mockito**
- **Spring Web**
- **Bean Validation**

## 📖 Para Estudiantes

Este proyecto demuestra:

1. Cómo estructurar un proyecto Java con Maven
2. Cómo crear una API REST con Spring Boot
3. Cómo escribir pruebas unitarias completas
4. Cómo usar mocks para aislar dependencias
5. Cómo probar controladores REST con MockMvc
6. Buenas prácticas de testing

## 🤝 Contribuciones

Este es un proyecto educativo. Siéntete libre de modificarlo y experimentar con diferentes casos de prueba.

## 📄 Licencia

Proyecto educativo - Uso libre para fines académicos.

