# 📚 Sistema de Evaluación de Estudiantes

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat&logo=java)
![SOLID](https://img.shields.io/badge/Principles-SOLID-blue?style=flat)
![Swing](https://img.shields.io/badge/GUI-Swing-green?style=flat)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat)

Sistema integral de evaluación académica que permite gestionar diferentes tipos de exámenes (escritos, orales y proyectos) aplicando los principios SOLID de programación orientada a objetos.

## 👥 Autores

- **Laura Vélez**

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Principios SOLID Aplicados](#-principios-solid-aplicados)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Uso del Sistema](#-uso-del-sistema)
- [Capturas de Pantalla](#-capturas-de-pantalla)
- [Extender el Sistema](#-extender-el-sistema)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Licencia](#-licencia)

---

## ✨ Características

### Funcionalidades Principales

- ✅ **Evaluación Multi-Formato**: Soporta 3 tipos de exámenes
    - 📝 Exámenes Escritos
    - 🎤 Exámenes Orales
    - 💼 Proyectos

- ✅ **Gestión de Estudiantes**: Registro completo con código, nombre y carrera

- ✅ **Sistema de Calificación Inteligente**:
    - Calificaciones de 0 a 100
    - Estados automáticos (Excelente, Aprobado, Suficiente, Reprobado)
    - Observaciones personalizadas según el tipo de examen

- ✅ **Interfaz Gráfica Moderna**:
    - Panel de evaluación interactivo
    - Tabla de resultados con filtros
    - Dashboard de estadísticas en tiempo real

- ✅ **Reportes y Estadísticas**:
    - Promedio general del sistema
    - Porcentaje de aprobación/reprobación
    - Historial completo de evaluaciones

---

## 🏗️ Principios SOLID Aplicados

Este proyecto es un ejemplo práctico de implementación de los 5 principios SOLID:

### 1️⃣ **SRP (Single Responsibility Principle)**
Cada clase tiene una única responsabilidad:

```java
// ✅ Estudiante: Solo datos del estudiante
public class Estudiante {
    private String nombre;
    private String codigo;
    private String carrera;
}

// ✅ ExamenEscrito: Solo lógica de exámenes escritos
public class ExamenEscrito implements IExamen {
    // Lógica específica...
}

// ✅ RegistroNotas: Solo gestión de registros
public class RegistroNotas implements IRegistroNotas {
    // Almacenamiento y consultas...
}
```

### 2️⃣ **OCP (Open/Closed Principle)**
Abierto para extensión, cerrado para modificación:

```java
// Para agregar un nuevo tipo de examen:
// 1. Crear nueva clase que implemente IExamen
public class ExamenPractico implements IExamen {
    // Nueva implementación
}

// 2. El resto del sistema NO necesita cambios
```

### 3️⃣ **LSP (Liskov Substitution Principle)**
Las subclases pueden sustituir a sus clases base:

```java
IExamen examen = new ExamenEscrito(...);  // ✅ Funciona
IExamen examen = new ExamenOral(...);     // ✅ Funciona
IExamen examen = new ExamenProyecto(...); // ✅ Funciona

// Todas pueden usarse intercambiablemente
evaluador.evaluarEstudiante(estudiante, examen, calificacion);
```

### 4️⃣ **ISP (Interface Segregation Principle)**
Interfaces específicas y cohesivas:

```java
// ❌ MAL: Interfaz gigante
interface IGestionCompleta {
    void evaluar();
    void registrar();
    void calcularPromedio();
    void generarReporte();
}

// ✅ BIEN: Interfaces específicas
interface IExamen { ... }
interface IEvaluador { ... }
interface IRegistroNotas { ... }
```

### 5️⃣ **DIP (Dependency Inversion Principle)**
Dependencia de abstracciones, no de concreciones:

```java
public class Main {
    // ✅ Dependemos de interfaces
    private IEvaluador evaluador;
    private IRegistroNotas registroNotas;
    
    public Main() {
        // Inyección de dependencias
        this.evaluador = new EvaluadorGeneral();
        this.registroNotas = new RegistroNotas();
    }
}
```

---

## 🔧 Requisitos

### Software Necesario

- **Java JDK 17+** (se usan Text Blocks y Pattern Matching)
- **IntelliJ IDEA** 2021.3+ (recomendado) o cualquier IDE Java
- **Maven** (opcional, para gestión de dependencias)

### Dependencias

El proyecto usa solo la biblioteca estándar de Java:
- `javax.swing.*` - Para la interfaz gráfica
- `java.time.*` - Para manejo de fechas
- `java.util.stream.*` - Para operaciones funcionales

---

## 📥 Instalación

### Opción 1: Clonar desde Git

```bash
# Clonar el repositorio
git clone https://github.com/lauvel19/Sistema-evaluacion.git

# Navegar al directorio
cd sistema-evaluacion-estudiantes

# Abrir en IntelliJ IDEA
idea .
```

### Opción 2: Descargar ZIP

1. Descarga el proyecto como ZIP
2. Extrae el contenido
3. Abre IntelliJ IDEA
4. File → Open → Selecciona la carpeta del proyecto

### Opción 3: Crear desde cero

1. Crea un nuevo proyecto Java en IntelliJ
2. Crea la estructura de paquetes:
   ```
   src/
   ├── interfaces/
   ├── model/
   ├── exams/
   ├── services/
   └── Main.java
   ```
3. Copia cada archivo en su ubicación correspondiente

---

## 📂 Estructura del Proyecto

```
sistema-evaluacion-estudiantes/
│
├── src/
│   ├── interfaces/              # 🔌 Contratos del sistema
│   │   ├── IExamen.java        # Interfaz base para exámenes
│   │   ├── IEvaluador.java     # Interfaz para evaluadores
│   │   └── IRegistroNotas.java # Interfaz para registro
│   │
│   ├── model/                   # 📦 Modelos de datos
│   │   ├── Estudiante.java     # Datos del estudiante
│   │   └── ResultadoExamen.java # Resultado de evaluación
│   │
│   ├── exams/                   # 📝 Tipos de exámenes
│   │   ├── ExamenEscrito.java  # Examen escrito
│   │   ├── ExamenOral.java     # Examen oral
│   │   └── ExamenProyecto.java # Evaluación por proyecto
│   │
│   ├── services/                # ⚙️ Lógica de negocio
│   │   ├── EvaluadorGeneral.java # Coordinador de evaluaciones
│   │   └── RegistroNotas.java    # Gestor de registros
│   │
│   └── Main.java                # 🚀 Punto de entrada + GUI
│
├── README.md                    # 📖 Este archivo
└── .gitignore                   # 🚫 Archivos ignorados
```

---

## 🚀 Uso del Sistema

### Ejecutar el Programa

**Desde IntelliJ IDEA:**
1. Abre el proyecto
2. Busca `Main.java`
3. Click derecho → Run 'Main.main()'

**Desde Terminal:**
```bash
cd src
javac Main.java
java Main
```

### Funcionalidades

#### 1. 📝 Evaluar Estudiante

1. Ve a la pestaña **"📝 Evaluar"**
2. Completa el formulario:
    - Nombre del estudiante
    - Código único
    - Carrera
    - Tipo de examen (Escrito/Oral/Proyecto)
    - Calificación (0-100)
3. Click en **"✓ Evaluar Estudiante"**
4. El resultado aparecerá en el área de texto

#### 2. 📊 Ver Resultados

1. Ve a la pestaña **"📊 Resultados"**
2. Click en **"🔄 Actualizar Lista"**
3. Selecciona una fila
4. Click en **"🔍 Ver Detalle"** para información completa

#### 3. 📈 Estadísticas

1. Ve a la pestaña **"📈 Estadísticas"**
2. Click en **"📊 Actualizar Estadísticas"**
3. Verás:
    - Total de evaluaciones
    - Promedio general
    - Porcentaje de aprobados/reprobados

---

## 📸 Capturas de Pantalla

### Panel de Evaluación
```
╔════════════════════════════════════════════╗
║     RESULTADO DE LA EVALUACIÓN             ║
╚════════════════════════════════════════════╝

Estudiante: Juan Pérez
Código: EST001
Carrera: Ingeniería de Sistemas

Tipo de Examen: Examen Escrito
Calificación: 95.0/100
Estado: Excelente

Observaciones:
Dominio excepcional de la teoría y práctica...
```

### Tabla de Resultados
| Estudiante | Código | Tipo Examen | Calificación | Estado | Fecha |
|------------|--------|-------------|--------------|--------|-------|
| Juan Pérez | EST001 | Escrito | 95.0 | Excelente | 26/11/2024 |
| María López | EST002 | Oral | 88.0 | Excelente | 26/11/2024 |

---

## 🔨 Extender el Sistema

### Agregar un Nuevo Tipo de Examen

**Ejemplo: Examen Práctico de Laboratorio**

```java
// 1. Crear nueva clase en exams/
package exams;

import interfaces.IExamen;
import model.Estudiante;
import model.ResultadoExamen;

public class ExamenPractico implements IExamen {
    private String laboratorio;
    private int horasDuracion;
    
    public ExamenPractico(String laboratorio, int horasDuracion) {
        this.laboratorio = laboratorio;
        this.horasDuracion = horasDuracion;
    }
    
    @Override
    public ResultadoExamen evaluar(Estudiante estudiante, double calificacion) {
        String estado = calificacion >= 80 ? "Aprobado" : "Reprobado";
        String obs = "Examen práctico en " + laboratorio;
        return new ResultadoExamen(estudiante, getTipoExamen(), 
                                   calificacion, estado, obs);
    }
    
    @Override
    public String getTipoExamen() {
        return "Examen Práctico";
    }
    
    @Override
    public int getDuracionMinutos() {
        return horasDuracion * 60;
    }
}

// 2. Agregar en Main.java -> crearExamen()
case "Examen Práctico":
    return new ExamenPractico("Lab de Física", 3);
```

**¡Eso es todo! No necesitas modificar ninguna otra clase.**

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17+ | Lenguaje principal |
| **Swing** | Built-in | Interfaz gráfica |
| **Java Streams** | Built-in | Procesamiento de datos |
| **Java Time API** | Built-in | Manejo de fechas |

---

## 📊 Diagramas

### Diagrama de Clases (Simplificado)

```
┌─────────────────┐
│   <<interface>> │
│     IExamen     │
└────────┬────────┘
         │ implements
         ├──────────────┬──────────────┬─────────────
         │              │              │
┌────────▼────────┐ ┌──▼──────────┐ ┌─▼─────────────┐
│ ExamenEscrito   │ │ ExamenOral  │ │ExamenProyecto │
└─────────────────┘ └─────────────┘ └───────────────┘

┌─────────────────┐         ┌──────────────────┐
│  IEvaluador     │◄────────┤EvaluadorGeneral  │
└─────────────────┘         └──────────────────┘

┌─────────────────┐         ┌──────────────────┐
│IRegistroNotas   │◄────────┤  RegistroNotas   │
└─────────────────┘         └──────────────────┘
```

---

## 🧪 Testing

### Casos de Prueba Recomendados

```java
// Test 1: Validación de calificaciones
- Calificación = -10 → Error
- Calificación = 0 → Válido
- Calificación = 100 → Válido
- Calificación = 150 → Error

// Test 2: Diferentes tipos de examen
- Examen Escrito con 95 → "Excelente"
- Examen Oral con 70 → "Aprobado"
- Proyecto con 50 → "Reprobado"

// Test 3: Estadísticas
- 0 evaluaciones → "No hay resultados"
- 5 aprobados, 2 reprobados → 71.4% aprobación
```

---

## 🐛 Solución de Problemas

### Error: "Cannot find symbol IExamen"
**Solución:** Verifica que todos los archivos estén en los paquetes correctos.

### Error: "Text blocks are not supported in Java 11"
**Solución:** Actualiza a Java 13+ o reemplaza text blocks con concatenación.

### La ventana no se muestra
**Solución:** Verifica que estés ejecutando desde el método `main()`.

---

## 📝 Mejoras Futuras

- [ ] Persistencia de datos (Base de datos o archivos)
- [ ] Autenticación de usuarios (profesores/administradores)
- [ ] Exportar reportes a PDF
- [ ] Gráficos estadísticos con JFreeChart
- [ ] Sistema de notificaciones por email
- [ ] Integración con API REST
- [ ] Aplicación móvil complementaria

---

## 🤝 Contribuir

¿Quieres contribuir? ¡Genial! Sigue estos pasos:

1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/NuevaCaracteristica`)
3. Commit tus cambios (`git commit -m 'Agregar nueva característica'`)
4. Push a la rama (`git push origin feature/NuevaCaracteristica`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver archivo `LICENSE` para más detalles.

```
MIT License

Copyright (c) 2024 Laura Vélez

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

---

## 📧 Contacto

**Laura Vélez** - [@lauravelez](https://github.com/lauravelez)

**Link del Proyecto:** [https://github.com/lauvel19/Sistema-evaluacion.git)

---

## 🙏 Agradecimientos

- Inspirado en sistemas académicos reales
- Principios SOLID por Robert C. Martin
- Comunidad de Java y programación orientada a objetos

---

<div align="center">

**⭐ Si te gustó este proyecto, dale una estrella en GitHub ⭐**

Hecho con ❤️ por Laura Vélez

</div>