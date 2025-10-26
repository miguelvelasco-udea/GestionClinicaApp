package com.clinica.service;

import com.clinica.model.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacienteService implements IPacienteService{
    private List<Paciente> pacientes = new ArrayList<>();

    @Override
    public Paciente registrarPaciente(Paciente paciente) throws Exception {
        if (paciente == null) {
            throw new Exception("El paciente no puede ser nulo.");
        }
        if (paciente.getDocumento() == null || paciente.getDocumento().isEmpty()) {
            throw new Exception("El documento del paciente es obligatorio.");
        }
          for (Paciente p : pacientes) {
            if (p.getDocumento().equals(paciente.getDocumento())) {
                throw new Exception("Ya existe un paciente con ese documento.");
            }
        }
        pacientes.add(paciente);
        return paciente;
    }

        @Override
    public Paciente actualizarPaciente(Paciente paciente) throws Exception {
        if (paciente == null || paciente.getDocumento() == null || paciente.getDocumento().isEmpty()) {
            throw new Exception("Datos del paciente inválidos.");
        }

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getDocumento().equals(paciente.getDocumento())) {
                pacientes.set(i, paciente);
                return paciente;
            }
        }

        throw new Exception("No se encontró el paciente para actualizar.");
    }

    @Override
    public void eliminarPaciente(String documento) throws Exception { // Cambiado a String
        boolean eliminado = pacientes.removeIf(p -> p.getDocumento().equals(documento));
        if (!eliminado) {
            throw new Exception("No se encontró el paciente con documento: " + documento);
    }
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
        return new ArrayList<>(pacientes);
    }
}   