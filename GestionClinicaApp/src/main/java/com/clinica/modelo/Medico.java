/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.modelo;

/**
 *
 * @author amyhe
 */
public class Medico {
    private String documento;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Especialidad especialidad;
    
    // Constructores
    public Medico() {}
    
    public Medico(String documento, String nombre, String apellido,String email, String telefono, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.email = email;
        this.telefono = telefono;
        this.especialidad = especialidad;
    }
    
    // Getters y Setters

    public String getDocumento() {
        return documento;
    }
        
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    @Override
    public String toString() {
        return getNombreCompleto() + "(" + especialidad.getNombre() + ")";
    }
    
}