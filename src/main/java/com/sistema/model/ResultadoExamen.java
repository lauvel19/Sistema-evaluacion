package com.sistema.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa el resultado de un examen.
 * SRP: Solo maneja datos del resultado de una evaluación.
 */
public class ResultadoExamen {
    private Estudiante estudiante;
    private String tipoExamen;
    private double calificacion;
    private String estado; // "Aprobado", "Reprobado", "Excelente"
    private LocalDateTime fechaEvaluacion;
    private String observaciones;

    public ResultadoExamen(Estudiante estudiante, String tipoExamen,
                           double calificacion, String estado, String observaciones) {
        this.estudiante = estudiante;
        this.tipoExamen = tipoExamen;
        this.calificacion = calificacion;
        this.estado = estado;
        this.fechaEvaluacion = LocalDateTime.now();
        this.observaciones = observaciones;
    }

    // Getters
    public Estudiante getEstudiante() {
        return estudiante;
    }

    public String getTipoExamen() {
        return tipoExamen;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaEvaluacion.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %.1f - %s - %s",
                estudiante.getNombre(),
                tipoExamen,
                calificacion,
                estado,
                getFechaFormateada());
    }
}