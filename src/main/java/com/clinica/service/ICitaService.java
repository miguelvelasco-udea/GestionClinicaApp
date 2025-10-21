package com.clinica.service;

import java.time.LocalDate;
import java.util.List;
import com.clinica.model.Cita;

public interface ICitaService {
    void crearCita(Cita cita);
    void cancelarCita(int id);
    void reprogramarCita(int id, LocalDate nuevaFecha);
    Cita buscarPorId(int id);
    List<Cita> listarCitas();
}

