# Cómo Ver la Cobertura de Tests

Este proyecto está configurado con **JaCoCo** (Java Code Coverage), una herramienta que mide qué porcentaje de tu código está cubierto por tests.

## 📊 Resumen de Cobertura Actual

Según el último reporte generado:

| Paquete | Instrucciones | Branches | Líneas | Métodos | Clases |
|---------|--------------|----------|--------|---------|--------|
| **Total** | **97%** | **91%** | **88%** | **100%** | **80%** |
| `controller` | 100% | 100% | 100% | 100% | 100% |
| `model` | 100% | n/a | 100% | 100% | 100% |
| `service` | 98% | 90% | 96% | 100% | 100% |
| `EmailApiApplication` | 0% | n/a | 0% | 0% | 0% |

## 🚀 Cómo Generar el Reporte de Cobertura

### Opción 1: Comando Maven (Recomendado)

```bash
mvn clean test
```

Este comando:
1. Limpia el directorio `target/`
2. Compila el código fuente y los tests
3. Ejecuta todos los tests
4. **Genera automáticamente el reporte de cobertura**

### Opción 2: Solo Generar Reporte (si ya ejecutaste los tests)

```bash
mvn jacoco:report
```

## 📁 Dónde Ver el Reporte

El reporte HTML se genera en:
```
target/site/jacoco/index.html
```

### Abrir el Reporte

**En macOS/Linux:**
```bash
open target/site/jacoco/index.html
```

**En Windows:**
```bash
start target/site/jacoco/index.html
```

**O simplemente:**
- Navega a la carpeta `target/site/jacoco/` en tu explorador de archivos
- Abre el archivo `index.html` con tu navegador web

## 📈 Qué Muestra el Reporte

El reporte HTML incluye:

### 1. **Vista General (index.html)**
- Tabla con cobertura por paquete
- Porcentajes de:
  - **Instrucciones**: Líneas de código ejecutadas
  - **Branches**: Ramas condicionales cubiertas (if/else, switch, etc.)
  - **Líneas**: Líneas de código cubiertas
  - **Métodos**: Métodos ejecutados
  - **Clases**: Clases con al menos un método ejecutado

### 2. **Vista por Paquete**
- Cobertura detallada de cada paquete
- Enlaces a cada clase

### 3. **Vista por Clase**
- Cobertura línea por línea
- Código fuente coloreado:
  - 🟢 **Verde**: Línea cubierta por tests
  - 🔴 **Rojo**: Línea NO cubierta por tests
  - 🟡 **Amarillo**: Línea parcialmente cubierta (solo algunas ramas)

### 4. **Vista de Código Fuente**
- Muestra el código fuente con indicadores de cobertura
- Números de línea con contadores de ejecución

## 📊 Interpretar los Números

### Instrucciones (Instructions)
- **97%**: 343 de 353 instrucciones fueron ejecutadas
- Mide las instrucciones bytecode ejecutadas

### Branches (Ramas)
- **91%**: 33 de 36 ramas fueron cubiertas
- Mide las decisiones condicionales (if/else, switch, operadores ternarios)

### Líneas (Lines)
- **88%**: 88 de 100 líneas fueron ejecutadas
- Mide las líneas de código fuente ejecutadas

### Métodos (Methods)
- **100%**: Todos los métodos fueron ejecutados al menos una vez

### Clases (Classes)
- **80%**: 4 de 5 clases fueron cubiertas
- `EmailApiApplication` no tiene tests (es normal, es solo la clase main)

## 🎯 Objetivos de Cobertura

El proyecto está configurado para requerir **mínimo 80% de cobertura de líneas**. Si la cobertura baja de este umbral, el build fallará.

Puedes ajustar este umbral en `pom.xml`:
```xml
<minimum>0.80</minimum>  <!-- Cambia a 0.90 para 90%, etc. -->
```

## 🔍 Ejemplo de Uso

1. **Ejecuta los tests con cobertura:**
   ```bash
   mvn clean test
   ```

2. **Abre el reporte:**
   ```bash
   open target/site/jacoco/index.html
   ```

3. **Navega por el reporte:**
   - Haz clic en un paquete para ver sus clases
   - Haz clic en una clase para ver el código fuente con colores
   - Las líneas rojas indican código no cubierto

## 📝 Archivos Generados

Después de ejecutar `mvn test`, encontrarás:

- `target/jacoco.exec` - Datos de ejecución (binario)
- `target/site/jacoco/index.html` - **Reporte principal HTML**
- `target/site/jacoco/jacoco.csv` - Reporte en formato CSV
- `target/site/jacoco/jacoco.xml` - Reporte en formato XML (para CI/CD)
- `target/site/jacoco/com.university.email.*/` - Reportes por paquete

## 🛠️ Comandos Útiles

| Comando | Descripción |
|---------|-------------|
| `mvn clean test` | Limpia, compila, ejecuta tests y genera reporte |
| `mvn test` | Ejecuta tests y genera reporte (sin limpiar) |
| `mvn jacoco:report` | Solo genera el reporte (requiere que ya se hayan ejecutado tests) |
| `mvn jacoco:check` | Verifica si la cobertura cumple con los umbrales mínimos |

## 💡 Tips

1. **Abre el reporte después de cada cambio** para ver qué código nuevo necesita tests
2. **Las líneas rojas** son oportunidades para agregar más tests
3. **La clase `EmailApiApplication`** normalmente no se prueba (es solo el punto de entrada)
4. **El reporte se regenera** cada vez que ejecutas `mvn test`

## 🚨 Solución de Problemas

**Si el reporte no se genera:**
- Asegúrate de que los tests se ejecutaron correctamente
- Verifica que el plugin JaCoCo esté en `pom.xml`
- Ejecuta `mvn clean test` para regenerar todo

**Si la cobertura es muy baja:**
- Revisa qué clases tienen líneas rojas en el reporte
- Agrega tests para cubrir esas líneas
- El reporte te muestra exactamente qué código no está cubierto

---

**¡Listo!** Ahora puedes monitorear la cobertura de tus tests fácilmente. 🎉

