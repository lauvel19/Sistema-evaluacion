package com.sistema.services;

import com.sistema.interfaces.IEvaluador;
import com.sistema.interfaces.IExamen;
import com.sistema.model.Estudiante;
import com.sistema.model.ResultadoExamen;

/**
 * Servicio que coordina la evaluación de estudiantes.
 * SRP: Solo se encarga de coordinar evaluaciones.
 * DIP: Depende de IExamen (abstracción) no de clases concretas.
 * OCP: Puede trabajar con cualquier tipo de examen sin modificarse.
 */
public class EvaluadorGeneral implements IEvaluador {

    @Override
    public ResultadoExamen evaluarEstudiante(Estudiante estudiante,
                                             IExamen examen,
                                             double calificacion) {
        // Validar calificación
        if (calificacion < 0 || calificacion > 100) {
            throw new IllegalArgumentException(
                    "La calificación debe estar entre 0 y 100");
        }

        // Validar estudiante
        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante no puede ser nulo");
        }

        // Delegar la evaluación al tipo específico de examen
        // Aquí vemos DIP en acción: no nos importa el tipo concreto
        return examen.evaluar(estudiante, calificacion);
    }

    /**
     * Método auxiliar para obtener información del examen.
     */
    public String obtenerInfoExamen(IExamen examen) {
        return String.format("Tipo: %s, Duración: %d minutos",
                examen.getTipoExamen(),
                examen.getDuracionMinutos());
    }
}
