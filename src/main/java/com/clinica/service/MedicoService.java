package com.clinica.service;

import com.clinica.dao.MedicoDAO;
import com.clinica.model.Medico;
import java.util.List;
import java.util.stream.Collectors;

public class MedicoService implements IMedicoService {
    private MedicoDAO medicoDAO;

    public MedicoService() {
        this.medicoDAO = new MedicoDAO();
    }

    @Override
    public Medico registrarMedico(Medico medico) throws Exception {
        validarMedico(medico, true);
        medicoDAO.agregarMedico(medico);
        return medico;
    }

    @Override
    public Medico actualizarMedico(Medico medico) throws Exception {
        validarMedico(medico, false);
        medicoDAO.actualizarMedico(medico);
        return medico;
    }

    @Override
    public void eliminarMedico(String documento) throws Exception {
        if (documento == null || documento.isEmpty()) {
            throw new Exception("El documento no puede ser nulo o vacío.");
        }

        if (medicoDAO.obtenerMedicos().stream().noneMatch(m -> m.getDocumento().equals(documento))) {
            throw new Exception("No se encontró el médico para eliminar.");
        }
        medicoDAO.eliminarMedico(documento);
    }

    @Override
    public Medico buscarPorDocumento(String documento) {
        if (documento == null || documento.isEmpty()) {
            return null;
        }

        return medicoDAO.obtenerMedicos().stream()
                .filter(m -> documento.equals(m.getDocumento()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicoDAO.obtenerMedicos();
    }

    @Override
    public List<Medico> listarPorEspecialidad(String nombreEspecialidad) {
        return medicoDAO.obtenerMedicos().stream()
                .filter(m -> m.getEspecialidad() != null &&
                             m.getEspecialidad().getNombre().equalsIgnoreCase(nombreEspecialidad))
                .collect(Collectors.toList());
    }

    //  Validaciones embebidas
    private void validarMedico(Medico medico, boolean esNuevo) throws Exception {
        if (medico == null) throw new Exception("El médico no puede ser nulo.");

        String doc = medico.getDocumento();
        String nombre = medico.getNombre();
        String apellido = medico.getApellido();
        String email = medico.getEmail();
        String telefono = medico.getTelefono();

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

        if (esNuevo && medicoDAO.obtenerMedicos().stream().anyMatch(m -> m.getDocumento().equals(doc))) {
            throw new Exception("Ya existe un médico con ese documento.");
        }

        if (!esNuevo && medicoDAO.obtenerMedicos().stream().noneMatch(m -> m.getDocumento().equals(doc))) {
            throw new Exception("No se encontró el médico para actualizar.");
        }
    }
}
