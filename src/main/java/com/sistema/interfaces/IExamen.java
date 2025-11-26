package com.sistema.interfaces;


import com.sistema.model.Estudiante;
import com.sistema.model.ResultadoExamen;

/**
 * Interfaz base para todos los tipos de exámenes.
 * DIP: El sistema depende de esta abstracción, no de implementaciones concretas.
 * ISP: Define solo lo esencial que cualquier examen debe tener.
 */
public interface IExamen {
    /**
     * Evalúa a un estudiante y retorna el resultado.
     * @param estudiante El estudiante a evaluar
     * @param calificacion La calificación obtenida (0-100)
     * @return ResultadoExamen con los detalles de la evaluación
     */
    ResultadoExamen evaluar(Estudiante estudiante, double calificacion);

    /**
     * Obtiene el nombre del tipo de examen.
     * @return String con el nombre del examen
     */
    String getTipoExamen();

    /**
     * Obtiene la duración estimada del examen en minutos.
     * @return Duración en minutos
     */
    int getDuracionMinutos();
}