/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.modelo;

/**
 *
 * @author amyhe
 */
public class Consulta {
    private final int id;
    private final Cita cita;
    private String diagnostico;
    private String receta;

    public Consulta(int id, Cita cita, String diagnostico, String receta) {
        this.id = id;
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.receta = receta;
    }

    public int getId() { 
        return id; 
    }
    public Cita getCita() { 
        return cita; 
    }
    public String getDiagnostico() { 
        return diagnostico; 
    }
    public String getReceta() { 
        return receta; 
    }

    public void setDiagnostico(String diagnostico) { 
        this.diagnostico = diagnostico; 
    }
    public void setReceta(String receta) { 
        this.receta = receta; 
    }

    @Override
    public String toString() {
        return "Consulta #" + id + " | Paciente: " + cita.getPaciente().getNombre() +
               " | Médico: " + cita.getMedico().getNombre() +
               " | Diagnóstico: " + diagnostico;

    }


}
