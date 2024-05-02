package com.example.taskmanagement.models;

import java.time.LocalDate;

public class Limpieza {
    private LocalDate fecha;
    private int habitacion;
    private String observaciones;

    public Limpieza(int habitacion, String observaciones, LocalDate fecha) {
        this.habitacion = habitacion;
        this.observaciones = observaciones;
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
