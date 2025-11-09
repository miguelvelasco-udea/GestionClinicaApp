package com.clinica.service;

import com.clinica.dao.PacienteDAO;
import com.clinica.model.Paciente;
import java.util.List;

public class PacienteService implements IPacienteService {
    private PacienteDAO pacienteDAO;

    public PacienteService() {
        this.pacienteDAO = new PacienteDAO();
    }

    @Override
    public Paciente registrarPaciente(Paciente paciente) throws Exception {
        validarPaciente(paciente, true);
        pacienteDAO.agregarPaciente(paciente);
        return paciente;
    }

    @Override
    public Paciente actualizarPaciente(Paciente paciente) throws Exception {
        validarPaciente(paciente, false);
        pacienteDAO.actualizarPaciente(paciente);
        return paciente;
    }

    @Override
    public void eliminarPaciente(String documento) throws Exception {
        if (pacienteDAO.obtenerPacientes().stream().noneMatch(p -> p.getDocumento().equals(documento))) {
            throw new Exception("No se encontró el paciente con documento: " + documento);
        }
        pacienteDAO.eliminarPaciente(documento);
    }

    @Override
    public Paciente buscarPorDocumento(String documento) {
        return pacienteDAO.obtenerPacientes().stream()
                .filter(p -> p.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteDAO.obtenerPacientes();
    }

    // ✅ Validaciones embebidas
    private void validarPaciente(Paciente paciente, boolean esNuevo) throws Exception {
        if (paciente == null) throw new Exception("El paciente no puede ser nulo.");

        String doc = paciente.getDocumento();
        String nombre = paciente.getNombre();
        String apellido = paciente.getApellido();
        String email = paciente.getEmail();
        String telefono = paciente.getTelefono();

        if (doc == null || !doc.matches("\\d{8,10}")) {
            throw new Exception("Documento inválido (8-10 dígitos numéricos).");
        }
        if (nombre == null || !nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,20}$")) {
            throw new Exception("Nombre inválido (solo letras, 3-20 caracteres).");
        }
        if (apellido == null || !apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,20}$")) {
            throw new Exception("Apellido inválido (solo letras, 3-20 caracteres).");
        }
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.com$")) {
            throw new Exception("Correo inválido (debe tener @ y terminar en .com).");
        }
        if (telefono == null || !telefono.matches("\\d{8,10}")) {
            throw new Exception("Teléfono inválido (8-10 dígitos numéricos).");
        }

        if (esNuevo && pacienteDAO.obtenerPacientes().stream().anyMatch(p -> p.getDocumento().equals(doc))) {
            throw new Exception("Ya existe un paciente con ese documento.");
        }

        if (!esNuevo && pacienteDAO.obtenerPacientes().stream().noneMatch(p -> p.getDocumento().equals(doc))) {
            throw new Exception("No se encontró el paciente para actualizar.");
        }
    }
}
