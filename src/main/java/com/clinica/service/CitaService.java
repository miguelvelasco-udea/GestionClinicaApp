package com.clinica.service;

import com.clinica.dao.CitaDAO;
import com.clinica.model.Cita;
import com.clinica.model.EstadoCita;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CitaService implements ICitaService {

    private CitaDAO citaDAO;

    public CitaService() {
        this.citaDAO = new CitaDAO();
    }

    @Override
    public void crearCita(Cita cita) throws Exception {
        validarCita(cita, true);
        citaDAO.agregarCita(cita);
    }

    @Override
    public void cancelarCita(int id) throws Exception {
        if (id <= 0) throw new Exception("ID de cita inválido");

        Cita cita = buscarPorId(id);
        if (cita == null) throw new Exception("Cita no encontrada con ID: " + id);

        if (cita.getEstado() == EstadoCita.CANCELADA) throw new Exception("La cita ya está cancelada");
        if (cita.getEstado() == EstadoCita.FINALIZADA) throw new Exception("No se puede cancelar una cita ya completada");

        cita.setEstado(EstadoCita.CANCELADA);
        citaDAO.actualizarCita(cita);
    }

    @Override
    public void reprogramarCita(int id, LocalDate nuevaFecha) throws Exception {
        if (id <= 0) throw new Exception("ID de cita inválido");
        if (nuevaFecha == null) throw new Exception("La nueva fecha no puede ser nula");
        if (nuevaFecha.isBefore(LocalDate.now())) throw new Exception("No se puede reprogramar a una fecha pasada");

        Cita cita = buscarPorId(id);
        if (cita == null) throw new Exception("Cita no encontrada con ID: " + id);

        if (cita.getEstado() == EstadoCita.CANCELADA) throw new Exception("No se puede reprogramar una cita cancelada");
        if (cita.getEstado() == EstadoCita.FINALIZADA) throw new Exception("No se puede reprogramar una cita completada");

        boolean existeDuplicado = citaDAO.obtenerCitas().stream()
            .anyMatch(c -> c.getPaciente().equals(cita.getPaciente()) &&
                           c.getFecha().equals(nuevaFecha) &&
                           c.getHora().equals(cita.getHora()) &&
                           c.getEstado() != EstadoCita.CANCELADA &&
                           c.getId() != id);

        if (existeDuplicado) throw new Exception("Ya existe una cita para este paciente en la nueva fecha y hora");

        cita.setFecha(nuevaFecha);
        citaDAO.actualizarCita(cita);
    }

    @Override
    public Cita buscarPorId(int id) {
        if (id <= 0) return null;
        return citaDAO.obtenerCitas().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaDAO.obtenerCitas();
    }

    //  Validación centralizada
    private void validarCita(Cita cita, boolean esNueva) throws Exception {
        if (cita == null) throw new Exception("La cita no puede ser nula");
        if (cita.getPaciente() == null || cita.getPaciente().getDocumento() == null ||
            !cita.getPaciente().getDocumento().matches("\\d{8,10}")) {
            throw new Exception("Paciente inválido o documento incorrecto");
        }
        if (cita.getMedico() == null || cita.getMedico().getDocumento() == null ||
            !cita.getMedico().getDocumento().matches("\\d{8,10}")) {
            throw new Exception("Médico inválido o documento incorrecto");
        }
        if (cita.getFecha() == null) throw new Exception("La fecha de la cita es obligatoria");
        if (cita.getFecha().isBefore(LocalDate.now())) throw new Exception("No se pueden crear citas en fechas pasadas");
        if (cita.getHora() == null) throw new Exception("La hora de la cita es obligatoria");
        if (cita.getHora().isBefore(LocalTime.of(6, 0)) || cita.getHora().isAfter(LocalTime.of(20, 0))) {
            throw new Exception("La hora debe estar entre 06:00 y 20:00");
        }
        if (esNueva && existeCitaDuplicada(cita)) {
            throw new Exception("Ya existe una cita para este paciente en la misma fecha y hora");
        }
    }

    private boolean existeCitaDuplicada(Cita cita) {
        return citaDAO.obtenerCitas().stream()
            .anyMatch(c -> c.getPaciente().equals(cita.getPaciente()) &&
                           c.getFecha().equals(cita.getFecha()) &&
                           c.getHora().equals(cita.getHora()) &&
                           c.getEstado() != EstadoCita.CANCELADA);
    }
}
