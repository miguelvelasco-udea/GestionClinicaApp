package com.clinica.service;

import com.clinica.model.HistoriaConsulta;
import java.util.ArrayList;
import java.util.List;

public class HistoriaConsultaService implements IHistoriaConsultaService {
    private List<HistoriaConsulta> historias = new ArrayList<>();
    private int contadorId = 1;

    @Override
    public HistoriaConsulta registrarHistoriaConsulta(HistoriaConsulta historia) throws Exception {
        if (historia == null) {
            throw new Exception("La historia clínica no puede ser nula");
        }
        
        if (historia.getCita() == null) {
            throw new Exception("La cita asociada es obligatoria");
        }
        
        if (historia.getDiagnostico() == null || historia.getDiagnostico().trim().isEmpty()) {
            throw new Exception("El diagnóstico es obligatorio");
        }
        
        // Verificar si ya existe una historia para esta cita
        if (buscarPorCita(historia.getCita().getId()) != null) {
            throw new Exception("Ya existe una historia clínica para esta cita");
        }
        
        historia.setId(contadorId++);
        historias.add(historia);
        return historia;
    }

    @Override
    public HistoriaConsulta actualizarHistoriaConsulta(HistoriaConsulta historia) throws Exception {
        if (historia == null || historia.getId() <= 0) {
            throw new Exception("Historia clínica inválida");
        }
        
        for (int i = 0; i < historias.size(); i++) {
            if (historias.get(i).getId() == historia.getId()) {
                historias.set(i, historia);
                return historia;
            }
        }
        
        throw new Exception("Historia clínica no encontrada con ID: " + historia.getId());
    }

    @Override
    public void eliminarHistoriaConsulta(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID de historia clínica inválido");
        }
        
        boolean eliminado = historias.removeIf(h -> h.getId() == id);
        if (!eliminado) {
            throw new Exception("Historia clínica no encontrada con ID: " + id);
        }
    }

    @Override
    public HistoriaConsulta buscarPorId(int id) {
        return historias.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<HistoriaConsulta> listarHistoriasConsulta() {
        return new ArrayList<>(historias);
    }

    @Override
    public List<HistoriaConsulta> buscarPorPaciente(String documentoPaciente) {
        List<HistoriaConsulta> resultado = new ArrayList<>();
        for (HistoriaConsulta historia : historias) {
            if (historia.getCita().getPaciente().getDocumento().equals(documentoPaciente)) {
                resultado.add(historia);
            }
        }
        return resultado;
    }

    @Override
    public List<HistoriaConsulta> buscarPorMedico(String documentoMedico) {
        List<HistoriaConsulta> resultado = new ArrayList<>();
        for (HistoriaConsulta historia : historias) {
            if (historia.getCita().getMedico().getDocumento().equals(documentoMedico)) {
                resultado.add(historia);
            }
        }
        return resultado;
    }

    @Override
    public HistoriaConsulta buscarPorCita(int idCita) {
        return historias.stream()
                .filter(h -> h.getCita().getId() == idCita)
                .findFirst()
                .orElse(null);
    }
}