package com.clinica.service;

import java.util.ArrayList;
import java.util.List;
import com.clinica.model.Medico;
import com.clinica.exception.BusinessException;
import com.clinica.model.Paciente;

public class PacienteService implements IPacienteService{
    private List<Paciente> pacientes = new ArrayList<>();

    @Override
    public Paciente registrarPaciente(Paciente paciente) throws BusinessException {
        if (paciente == null) {
            throw new BusinessException("El paciente no puede ser nulo.");
        }
        if (paciente.getDocumento() == null || paciente.getDocumento().isEmpty()) {
            throw new BusinessException("El documento del paciente es obligatorio.");
        }
          for (Paciente p : pacientes) {
            if (p.getDocumento().equals(paciente.getDocumento())) {
                throw new BusinessException("Ya existe un paciente con ese documento.");
            }
        }
        pacientes.add(paciente);
        return paciente;
    }

        @Override
    public Paciente actualizarPaciente(Paciente paciente) throws BusinessException {
        if (paciente == null || paciente.getIdPaciente() <= 0) {
            throw new BusinessException("Datos del paciente inválidos.");
        }

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getIdPaciente() == paciente.getIdPaciente()) {
                pacientes.set(i, paciente);
                return paciente;
            }
        }

        throw new BusinessException("No se encontró el paciente para actualizar.");
    }

    @Override
    public void eliminarPaciente(int idPaciente) throws BusinessException {
        boolean eliminado = pacientes.removeIf(p -> p.getIdPaciente() == idPaciente);
        if (!eliminado) {
            throw new BusinessException("No se encontró el paciente para eliminar.");
        }
    }
    @Override
    public Paciente buscarPorId(int idPaciente) {
        return pacientes.stream()
                .filter(p -> p.getIdPaciente() == idPaciente)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Paciente buscarPorDocumento(String documento) {
        return pacientes.stream()
                .filter(p -> p.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacientes;
    }
}   