package com.example.taskmanagement.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Trabajo
{

    private String codTrabajo;

    private String categoria;

    private String descripcion;

    private String fecIni;

    private String fecFin;

    private BigDecimal tiempo;
    private int prioridad;

    private Trabajador idTrabajador;

    public Trabajo() {
    }

    public Trabajo(String codTrabajo, String categoria, String descripcion, String fecIni, String fecFin, BigDecimal tiempo, int prioridad, Trabajador idTrabajador) {
        this.codTrabajo = codTrabajo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecIni = fecIni;
        this.fecFin = fecFin;
        this.tiempo = tiempo;
        this.prioridad = prioridad;
        this.idTrabajador = idTrabajador;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getCodTrabajo()
    {
        return codTrabajo;
    }

    public void setCodTrabajo(String codTrabajo)
    {
        this.codTrabajo = codTrabajo;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getFecIni()
    {
        return fecIni;
    }

    public void setFecIni(String fecIni)
    {
        this.fecIni = fecIni;
    }

    public String getFecFin()
    {
        return fecFin;
    }

    public void setFecFin(String fecFin)
    {
        this.fecFin = fecFin;
    }

    public BigDecimal getTiempo()
    {
        return tiempo;
    }

    public void setTiempo(BigDecimal tiempo)
    {
        this.tiempo = tiempo;
    }

    public Trabajador getIdTrabajador()
    {
        return idTrabajador;
    }

    public void setIdTrabajador(Trabajador idTrabajador)
    {
        this.idTrabajador = idTrabajador;
    }

}
