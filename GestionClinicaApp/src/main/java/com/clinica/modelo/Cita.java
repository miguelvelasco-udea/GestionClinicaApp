/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author amyhe
 */
public class Cita {
    private final int id;
    private LocalDate fecha;
    private LocalTime hora;
    private final Paciente paciente;
    private final Medico medico;
    private EstadoCita estado;

    public Cita(int id, LocalDate fecha, LocalTime hora, Paciente paciente, Medico medico) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = EstadoCita.PROGRAMADA;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

@Override
public String toString() {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    return "Cita #" + id + " - " + fecha.format(dateFormatter) + " " + hora.format(timeFormatter) +
               " | " + medico.getNombre() + " (" + medico.getEspecialidad().getNombre() + ")" +
               " | Paciente: " + paciente.getNombre() +
               " | Estado: " + estado;
}



}







