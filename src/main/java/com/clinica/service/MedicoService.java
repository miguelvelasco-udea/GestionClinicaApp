package com.clinica.service;

import java.util.ArrayList;
import java.util.List;
import com.clinica.model.Medico;
import com.clinica.exception.BusinessException;

public class MedicoService implements IMedicoService{
    private List<Medico> medicos = new ArrayList<>();

    @Override
    public Medico registrarMedico(Medico medico) throws BusinessException {
        if (medico == null) {
            throw new BusinessException("El médico no puede ser nulo.");
        }
        if (medico.getDocumento() == null || medico.getDocumento().isEmpty()) {
            throw new BusinessException("El documento del médico es obligatorio.");
        }
        for (Medico m : medicos) {
            if (m.getDocumento().equals(medico.getDocumento())) {
                throw new BusinessException("Ya existe un médico con ese documento.");
            }
        }

        medicos.add(medico);
        return medico;
    }
    @Override
    public Medico actualizarMedico(Medico medico) throws BusinessException {
        if (medico == null || medico.getIdMedico() <= 0) {
            throw new BusinessException("Datos del médico inválidos.");
        }

        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getIdMedico() == medico.getIdMedico()) {
                medicos.set(i, medico);
                return medico;
            }
        }

        throw new BusinessException("No se encontró el médico para actualizar.");
    }

    @Override
    public void eliminarMedico(int idMedico) throws BusinessException {
        boolean eliminado = medicos.removeIf(m -> m.getIdMedico() == idMedico);
        if (!eliminado) {
            throw new BusinessException("No se encontró el médico para eliminar.");
        }
    }

    @Override
    public Medico buscarPorId(int idMedico) {
        return medicos.stream()
                .filter(m -> m.getIdMedico() == idMedico)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Medico buscarPorDocumento(String documento) {
        return medicos.stream()
                .filter(m -> m.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicos;
    }

    @Override
    public List<Medico> listarPorEspecialidad(String nombreEspecialidad) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico m : medicos) {
            if (m.getEspecialidad() != null && 
                m.getEspecialidad().equalsIgnoreCase(nombreEspecialidad)) {
                resultado.add(m);
            }
        }
        return resultado;
    }
}