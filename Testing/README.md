# Test Doubles - Ejemplos Educativos

Este proyecto contiene ejemplos prácticos de los diferentes tipos de **Test Doubles** (dobles de prueba) en Python. Los test doubles son objetos que reemplazan dependencias reales durante las pruebas para aislar el código bajo test.

## 📚 ¿Qué son los Test Doubles?

Los test doubles son objetos que simulan el comportamiento de dependencias reales en un entorno de pruebas. Permiten:
- **Aislar** el código bajo prueba
- **Controlar** el comportamiento de las dependencias
- **Verificar** interacciones con dependencias
- **Acelerar** las pruebas al evitar llamadas costosas (red, base de datos, etc.)

## 🎯 Tipos de Test Doubles

Este proyecto incluye ejemplos de los 5 tipos principales de test doubles:

### 1. **Dummy** (`Dummy.py`)
Un objeto que se pasa como parámetro pero **nunca se utiliza**. Solo sirve para satisfacer la firma de una función o constructor.

**Características:**
- No implementa ninguna funcionalidad
- No se llama a ningún método
- Solo ocupa un lugar en la firma

**Ejemplo de uso:**
```python
dummy_client = DummySmtpClient()  # No hace nada
service = EmailService(dummy_client)
service.notify("Hola Mundo")  # Solo usa su propia lógica
```

---

### 2. **Stub** (`Stub.py`)
Un objeto que proporciona **respuestas predefinidas** a las llamadas de métodos. No verifica interacciones, solo devuelve valores hardcodeados.

**Características:**
- Devuelve valores predefinidos
- No verifica cómo se llama
- Simula respuestas simples

**Ejemplo de uso:**
```python
stub_client = StubSmtpClient()  # Siempre retorna True
service = EmailService(stub_client)
assert service.send_email("Hola") is True
```

---

### 3. **Fake** (`Fake.py`)
Una **implementación funcional simplificada** de la dependencia real. Tiene comportamiento real pero con limitaciones (por ejemplo, almacena en memoria en lugar de en base de datos).

**Características:**
- Implementa la funcionalidad real de forma simplificada
- Útil para pruebas de integración rápidas
- Mantiene estado interno

**Ejemplo de uso:**
```python
fake_client = FakeSmtpClient()  # Almacena mensajes en memoria
service = EmailService(fake_client)
service.send_email("Hola")
print(fake_client.sent_messages)  # ['Hola']
```

---

### 4. **Spy** (`Spy.py`)
Un objeto que **registra información** sobre cómo se llama (qué métodos, con qué parámetros, cuántas veces). Permite verificar interacciones después de la ejecución.

**Características:**
- Registra todas las interacciones
- Permite verificación posterior
- No falla durante la ejecución, solo al verificar

**Ejemplo de uso:**
```python
spy_client = SpySmtpClient()
service = EmailService(spy_client)
service.send_email("Hola")

# Verificación posterior
assert spy_client.was_called is True
assert spy_client.message_sent == "Hola"
```

---

### 5. **Mock** (`Mock.py`)
Un objeto que **define expectativas** antes de la ejecución y **verifica automáticamente** que se cumplan durante la ejecución. Falla inmediatamente si las expectativas no se cumplen.

**Características:**
- Define expectativas antes de la ejecución
- Verifica automáticamente durante la ejecución
- Falla inmediatamente si no se cumplen las expectativas

**Ejemplo de uso:**
```python
mock_client = MockSmtpClient()
mock_client.expect_send("Hola")  # Define expectativa

service = EmailService(mock_client)
service.send_email("Hola")  # Pasa si coincide
# Si message != "Hola" lanza AssertionError
```

---

## 📊 Comparación Rápida

| Tipo | Propósito | Verifica Interacciones | Tiene Comportamiento |
|------|-----------|------------------------|---------------------|
| **Dummy** | Satisfacer firma | ❌ | ❌ |
| **Stub** | Devolver valores | ❌ | ✅ (limitado) |
| **Fake** | Implementación simplificada | ❌ | ✅ (completo) |
| **Spy** | Registrar interacciones | ✅ (posterior) | ✅ |
| **Mock** | Verificar expectativas | ✅ (inmediato) | ✅ |

---

## 🚀 Cómo Usar Este Proyecto

Cada archivo es independiente y puede ejecutarse directamente:

```bash
# Ejecutar ejemplos individuales
python Doubles/Dummy.py
python Doubles/Stub.py
python Doubles/Fake.py
python Doubles/Spy.py
python Doubles/Mock.py
```

---

## 💡 Cuándo Usar Cada Tipo

- **Dummy**: Cuando necesitas pasar un parámetro pero no se usa
- **Stub**: Cuando solo necesitas que un método devuelva un valor específico
- **Fake**: Cuando necesitas comportamiento real pero simplificado (ej: base de datos en memoria)
- **Spy**: Cuando quieres verificar interacciones después de la ejecución
- **Mock**: Cuando quieres verificar que se llamen métodos específicos con parámetros específicos

---

## 📖 Referencias

Este proyecto está basado en los conceptos de test doubles descritos por:
- **Martin Fowler** en "Mocks Aren't Stubs"
- **Gerard Meszaros** en "xUnit Test Patterns"

---

## 📝 Notas

Todos los ejemplos utilizan un `EmailService` con una dependencia `SmtpClient` para demostrar cada patrón de manera consistente y fácil de entender.

