package com.example.taskmanagement.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Trabajo
{

    private String codTrabajo;

    private String categoria;

    private String descripcion;

    private LocalDate fecIni;

    private LocalDate fecFin;

    private BigDecimal tiempo;

    private Trabajador idTrabajador;

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

    public LocalDate getFecIni()
    {
        return fecIni;
    }

    public void setFecIni(LocalDate fecIni)
    {
        this.fecIni = fecIni;
    }

    public LocalDate getFecFin()
    {
        return fecFin;
    }

    public void setFecFin(LocalDate fecFin)
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
