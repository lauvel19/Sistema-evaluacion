package com.sistema.services;

import com.sistema.interfaces.IRegistroNotas;
import com.sistema.model.ResultadoExamen;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar el registro de notas.
 * SRP: Solo se encarga de almacenar y consultar resultados.
 * ISP: Implementa solo lo necesario para gestión de registros.
 */
public class RegistroNotas implements IRegistroNotas {
    private List<ResultadoExamen> resultados;

    public RegistroNotas() {
        this.resultados = new ArrayList<>();
    }

    @Override
    public void registrarResultado(ResultadoExamen resultado) {
        if (resultado == null) {
            throw new IllegalArgumentException("El resultado no puede ser nulo");
        }
        resultados.add(resultado);
    }

    @Override
    public List<ResultadoExamen> obtenerTodosLosResultados() {
        return new ArrayList<>(resultados); // Devolver copia para proteger datos
    }

    @Override
    public List<ResultadoExamen> obtenerResultadosPorEstudiante(String nombreEstudiante) {
        return resultados.stream()
                .filter(r -> r.getEstudiante().getNombre()
                        .equalsIgnoreCase(nombreEstudiante))
                .toList(); // ✅ CORRECCIÓN: Usar toList() en lugar de collect(Collectors.toList())
    }

    @Override
    public double calcularPromedio(String nombreEstudiante) {
        List<ResultadoExamen> resultadosEstudiante =
                obtenerResultadosPorEstudiante(nombreEstudiante);

        if (resultadosEstudiante.isEmpty()) {
            return 0.0;
        }

        double suma = resultadosEstudiante.stream()
                .mapToDouble(ResultadoExamen::getCalificacion)
                .sum();

        return suma / resultadosEstudiante.size();
    }

    /**
     * Obtiene estadísticas generales del sistema.
     */
    public String obtenerEstadisticas() {
        if (resultados.isEmpty()) {
            return "No hay resultados registrados";
        }

        double promedioGeneral = resultados.stream()
                .mapToDouble(ResultadoExamen::getCalificacion)
                .average()
                .orElse(0.0);

        long aprobados = resultados.stream()
                .filter(r -> r.getCalificacion() >= 60)
                .count();

        long reprobados = resultados.size() - aprobados;

        // ✅ CORRECCIÓN: Usar Text Block en lugar de concatenación
        return String.format("""
            Total evaluaciones: %d
            Promedio general: %.2f
            Aprobados: %d (%.1f%%)
            Reprobados: %d (%.1f%%)""",
                resultados.size(),
                promedioGeneral,
                aprobados,
                (aprobados * 100.0 / resultados.size()),
                reprobados,
                (reprobados * 100.0 / resultados.size())
        );
    }
}