package com.clinica.service;

import java.util.List;
import com.clinica.model.Paciente;
import com.clinica.exception.BusinessException;

public interface IPacienteService {
    
    Paciente registrarPaciente(Paciente paciente) throws BusinessException;
    
    Paciente actualizarPaciente(Paciente paciente) throws BusinessException;
    
    void eliminarPaciente(String documento) throws BusinessException;
    
    Paciente buscarPorDocumento(String documento);
    
    List<Paciente> listarPacientes();
}

