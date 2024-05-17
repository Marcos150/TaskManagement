package com.example.taskmanagement.models;

public class Limpieza {
    private String _id;
    private String fecha;
    private String habitacion;
    private String observaciones;
    private String __v;

    public Limpieza(String _id, String habitacion, String observaciones, String fecha, String __v) {
        this._id = _id;
        this.habitacion = habitacion;
        this.observaciones = observaciones;
        this.fecha = fecha;
        this.__v = __v;
    }

    public Limpieza() {}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String get_id()
    {
        return _id;
    }

    public void set_id(String _id)
    {
        this._id = _id;
    }

    public String get__v()
    {
        return __v;
    }

    public void set__v(String __v)
    {
        this.__v = __v;
    }
}
