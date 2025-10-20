/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.modelo;

/**
 *
 * @author amyhe
 */
public class Especialidad {
    private int id;
    private String nombre;
    private String descripcion;

    //Constructores
    public Especialidad() {}
    
    public Especialidad(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Getters y Setters 
    public int getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getDescripcion() { 
        return descripcion; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    @Override
    public String toString() {
        return nombre;
    }
}


