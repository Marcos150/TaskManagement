package com.example.taskmanagement.models;

import java.util.LinkedHashSet;
import java.util.Set;

public class Trabajador implements java.io.Serializable
{
    private String idTrabajador;


    private String dni;


    private String nombre;


    private String apellidos;


    private String especialidad;


    private String contrasenya;


    private String email;

    private Set<Trabajo> trabajos = new LinkedHashSet<>();

    public Trabajador() {
    }

    public Trabajador(String idTrabajador, String dni, String nombre, String apellidos, String especialidad, String contrasenya, String email, Set<Trabajo> trabajos) {
        this.idTrabajador = idTrabajador;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.contrasenya = contrasenya;
        this.email = email;
        this.trabajos = trabajos;
    }

    public String getIdTrabajador()
    {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador)
    {
        this.idTrabajador = idTrabajador;
    }

    public String getDni()
    {
        return dni;
    }

    public void setDni(String dni)
    {
        this.dni = dni;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellidos()
    {
        return apellidos;
    }

    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }

    public String getEspecialidad()
    {
        return especialidad;
    }

    public void setEspecialidad(String especialidad)
    {
        this.especialidad = especialidad;
    }

    public String getContrasenya()
    {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya)
    {
        this.contrasenya = contrasenya;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Set<Trabajo> getTrabajos()
    {
        return trabajos;
    }

    public void setTrabajos(Set<Trabajo> trabajos)
    {
        this.trabajos = trabajos;
    }

}
