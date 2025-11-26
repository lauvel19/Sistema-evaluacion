package com.sistema.interfaces;

import com.sistema.model.Estudiante;
import com.sistema.model.ResultadoExamen;

/**
 * Interfaz para evaluar estudiantes.
 * ISP: Interfaz específica para la responsabilidad de evaluar.
 * DIP: Las clases de alto nivel dependen de esta abstracción.
 */
public interface IEvaluador {
    /**
     * Evalúa a un estudiante con un tipo específico de examen.
     * @param estudiante El estudiante a evaluar
     * @param examen El tipo de examen a aplicar
     * @param calificacion La calificación obtenida
     * @return ResultadoExamen con los detalles
     */
    ResultadoExamen evaluarEstudiante(Estudiante estudiante, IExamen examen, double calificacion);
}