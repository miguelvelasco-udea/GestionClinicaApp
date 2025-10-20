/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.modelo;

import java.time.LocalDate;

/**
 *
 * @author amyhe
 */
public class Paciente {
    private String documento;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String email;
    private LocalDate fechaNacimiento;
    private String historialMedico;
    
    // Constructores
    public Paciente() {}

    public Paciente(String documento, String nombre, String apellido, String telefono, String direccion, String email, LocalDate fechaNacimiento, String historialMedico) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.historialMedico = historialMedico;
    }
    
    public String getDocumento() {
        return documento;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getHistorialMedico() {
        return historialMedico;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setHistorialMedico(String historialMedico) {
        this.historialMedico = historialMedico;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    @Override
    public String toString() {
        return getNombreCompleto() + "(" + documento + ")";
    }
}
