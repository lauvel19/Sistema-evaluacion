package com.sistema.model;


/**
 * Clase que representa a un estudiante.
 * SRP: Solo maneja datos del estudiante.
 */
public class Estudiante {
    private String nombre;
    private String codigo;
    private String carrera;

    public Estudiante(String nombre, String codigo, String carrera) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.carrera = carrera;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCarrera() {
        return carrera;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return nombre + " (" + codigo + ") - " + carrera;
    }
}