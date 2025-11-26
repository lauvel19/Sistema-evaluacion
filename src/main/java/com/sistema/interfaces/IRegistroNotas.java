package com.sistema.interfaces;

import com.sistema.model.ResultadoExamen;

import java.util.List;

/**
 * Interfaz para el registro de notas.
 * ISP: Interfaz específica para gestión de registros.
 * SRP: Separada de la lógica de evaluación.
 */
public interface IRegistroNotas {
    /**
     * Registra un resultado de examen.
     * @param resultado El resultado a registrar
     */
    void registrarResultado(ResultadoExamen resultado);

    /**
     * Obtiene todos los resultados registrados.
     * @return Lista de resultados
     */
    List<ResultadoExamen> obtenerTodosLosResultados();

    /**
     * Obtiene los resultados de un estudiante específico.
     * @param nombreEstudiante Nombre del estudiante
     * @return Lista de resultados del estudiante
     */
    List<ResultadoExamen> obtenerResultadosPorEstudiante(String nombreEstudiante);

    /**
     * Calcula el promedio general de un estudiante.
     * @param nombreEstudiante Nombre del estudiante
     * @return Promedio de calificaciones
     */
    double calcularPromedio(String nombreEstudiante);
}