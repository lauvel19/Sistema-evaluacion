package com.sistema.exams;

import com.sistema.interfaces.IExamen;
import com.sistema.model.Estudiante;
import com.sistema.model.ResultadoExamen;

/**
 * Implementación concreta de evaluación por proyecto.
 * SRP: Solo maneja la lógica específica de proyectos.
 * OCP: Extiende funcionalidad sin modificar la interfaz.
 * LSP: Puede sustituir a IExamen sin problemas.
 */
public class ExamenProyecto implements IExamen {
    private String nombreProyecto;
    private boolean esGrupal;
    private int numeroIntegrantes;

    public ExamenProyecto(String nombreProyecto, boolean esGrupal, int numeroIntegrantes) {
        this.nombreProyecto = nombreProyecto;
        this.esGrupal = esGrupal;
        this.numeroIntegrantes = esGrupal ? numeroIntegrantes : 1;
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
        return "Proyecto";
    }

    @Override
    public int getDuracionMinutos() {
        // Los proyectos son a largo plazo, se estima tiempo de presentación
        return esGrupal ? 45 : 30;
    }

    private String determinarEstado(double calificacion) {
        if (calificacion >= 88) return "Excelente";
        if (calificacion >= 75) return "Aprobado";
        if (calificacion >= 65) return "Suficiente";
        return "Reprobado";
    }

    private String generarObservaciones(double calificacion) {
        String tipo = esGrupal ?
                "Proyecto grupal (" + numeroIntegrantes + " integrantes)" :
                "Proyecto individual";

        if (calificacion >= 88) {
            return tipo + ": '" + nombreProyecto + "' muestra excelente calidad, " +
                    "creatividad e innovación. Implementación sobresaliente.";
        } else if (calificacion >= 75) {
            return tipo + ": '" + nombreProyecto + "' cumple los objetivos. " +
                    "Buena ejecución y documentación.";
        } else if (calificacion >= 65) {
            return tipo + ": '" + nombreProyecto + "' alcanza requisitos mínimos. " +
                    "Requiere mejorar documentación o implementación.";
        } else {
            return tipo + ": '" + nombreProyecto + "' no cumple expectativas. " +
                    "Necesita trabajo significativo.";
        }
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public boolean isEsGrupal() {
        return esGrupal;
    }

    public int getNumeroIntegrantes() {
        return numeroIntegrantes;
    }
}
