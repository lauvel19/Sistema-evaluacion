package com.sistema.exams;


import com.sistema.interfaces.IExamen;
import com.sistema.model.Estudiante;
import com.sistema.model.ResultadoExamen;

/**
 * Implementación concreta de un examen oral.
 * SRP: Solo maneja la lógica específica de exámenes orales.
 * OCP: Extiende funcionalidad sin modificar la interfaz.
 * LSP: Puede sustituir a IExamen sin problemas.
 */
public class ExamenOral implements IExamen {
    private String temaEvaluado;
    private boolean conDefensaTesis;

    public ExamenOral(String temaEvaluado, boolean conDefensaTesis) {
        this.temaEvaluado = temaEvaluado;
        this.conDefensaTesis = conDefensaTesis;
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
        return "Examen Oral";
    }

    @Override
    public int getDuracionMinutos() {
        return conDefensaTesis ? 60 : 30;
    }

    private String determinarEstado(double calificacion) {
        if (calificacion >= 85) return "Excelente";
        if (calificacion >= 70) return "Aprobado";
        if (calificacion >= 60) return "Suficiente";
        return "Reprobado";
    }

    private String generarObservaciones(double calificacion) {
        String defensa = conDefensaTesis ? " con defensa de tesis" : "";

        if (calificacion >= 85) {
            return "Excelente expresión oral y dominio del tema: " + temaEvaluado +
                    defensa + ". Argumentación sólida y clara.";
        } else if (calificacion >= 70) {
            return "Buena comunicación sobre " + temaEvaluado + defensa +
                    ". Puede mejorar fluidez.";
        } else if (calificacion >= 60) {
            return "Conocimiento básico de " + temaEvaluado +
                    ". Requiere mejorar claridad expositiva.";
        } else {
            return "Dificultades en expresión oral. Necesita preparación adicional.";
        }
    }

    public String getTemaEvaluado() {
        return temaEvaluado;
    }

    public boolean isConDefensaTesis() {
        return conDefensaTesis;
    }
}