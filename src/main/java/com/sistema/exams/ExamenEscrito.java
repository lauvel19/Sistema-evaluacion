package com.sistema.exams;


import com.sistema.interfaces.IExamen;
import com.sistema.model.Estudiante;
import com.sistema.model.ResultadoExamen;

/**
 * Implementación concreta de un examen escrito.
 * SRP: Solo maneja la lógica específica de exámenes escritos.
 * OCP: Extiende funcionalidad sin modificar la interfaz.
 * LSP: Puede sustituir a IExamen sin problemas.
 */
public class ExamenEscrito implements IExamen {
    private int numeroPreguntasTeoria;
    private int numeroPreguntasPractica;

    public ExamenEscrito(int numeroPreguntasTeoria, int numeroPreguntasPractica) {
        this.numeroPreguntasTeoria = numeroPreguntasTeoria;
        this.numeroPreguntasPractica = numeroPreguntasPractica;
    }

    @Override
    public ResultadoExamen evaluar(Estudiante estudiante, double calificacion) {
        String estado = determinarEstado(calificacion);
        String observaciones = generarObservaciones(calificacion);

        return new ResultadoExamen(
                estudiante,
                getTipoExamen(),
                calificacion,
                estado,
                observaciones
        );
    }

    @Override
    public String getTipoExamen() {
        return "Examen Escrito";
    }

    @Override
    public int getDuracionMinutos() {
        return 90; // 1.5 horas típicas para un examen escrito
    }

    private String determinarEstado(double calificacion) {
        if (calificacion >= 90) return "Excelente";
        if (calificacion >= 70) return "Aprobado";
        if (calificacion >= 60) return "Suficiente";
        return "Reprobado";
    }

    private String generarObservaciones(double calificacion) {
        if (calificacion >= 90) {
            return "Dominio excepcional de la teoría y práctica. " +
                    numeroPreguntasTeoria + " preguntas de teoría, " +
                    numeroPreguntasPractica + " de práctica.";
        } else if (calificacion >= 70) {
            return "Buen conocimiento del contenido. Revisar áreas de mejora en práctica.";
        } else if (calificacion >= 60) {
            return "Conocimientos básicos alcanzados. Requiere refuerzo.";
        } else {
            return "Debe estudiar más el material. Considerar tutoría.";
        }
    }

    public int getNumeroPreguntasTeoria() {
        return numeroPreguntasTeoria;
    }

    public int getNumeroPreguntasPractica() {
        return numeroPreguntasPractica;
    }
}
