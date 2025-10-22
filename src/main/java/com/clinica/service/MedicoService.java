package com.clinica.service;

import com.clinica.exception.BusinessException;
import com.clinica.model.Medico;
import java.util.ArrayList;
import java.util.List;

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
        if (medico == null || medico.getDocumento()== null || medico.getDocumento().isEmpty()) {
            throw new BusinessException("Datos del médico inválidos.");
        }

        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getDocumento().equals(medico.getDocumento())) {
                medicos.set(i, medico);
                return medico;
            }
        }

        throw new BusinessException("No se encontró el médico para actualizar.");
    }

    @Override
    public void eliminarMedico(String documento) throws BusinessException {
        if (documento == null || documento.isEmpty()) {
            throw new BusinessException("El documento no puede ser nulo o vacío.");
        }
        
        boolean eliminado = medicos.removeIf(m -> m.getDocumento().equals(documento));
        if (!eliminado){
            throw new BusinessException("No se encontró el médico para eliminar.");
        }
    }

    @Override
    public Medico buscarPorDocumento(String documento) {
        if (documento == null || documento.isEmpty()) {
            return null;
        }
        
        return medicos.stream()
                .filter(m -> documento.equals(m.getDocumento()))
                .findFirst()
                .orElse(null);
    }
    

    @Override
    public List<Medico> listarMedicos() {
        return new ArrayList<>(medicos);
    }

    @Override
    public List<Medico> listarPorEspecialidad(String nombreEspecialidad) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico m : medicos) {
            if (m.getEspecialidad() != null && 
                m.getEspecialidad().getNombre().equalsIgnoreCase(nombreEspecialidad)) {
                resultado.add(m);
            }
        }
        return resultado;
    }
}