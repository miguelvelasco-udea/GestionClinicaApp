package com.clinica.service;

import com.clinica.exception.BusinessException;
import com.clinica.model.Medico;
import java.util.List;

public interface IMedicoService {
    Medico registrarMedico(Medico medico) throws BusinessException;
    Medico actualizarMedico(Medico medico) throws BusinessException;
    void eliminarMedico(String documento) throws BusinessException;
    Medico buscarPorDocumento(String documento);
    List<Medico> listarMedicos();
    List<Medico> listarPorEspecialidad(String nombreEspecialidad);
}