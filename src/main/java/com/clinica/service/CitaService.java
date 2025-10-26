package com.clinica.service;
import com.clinica.model.Cita;
import com.clinica.model.EstadoCita;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CitaService implements ICitaService {

    private List<Cita> citas = new ArrayList<>();
    private int contadorId = 1;

   @Override
    public void crearCita(Cita cita) throws Exception {
        // Validar que la cita no sea nula
        if (cita == null) {
            throw new Exception("La cita no puede ser nula");
        }
        
        // Validar campos obligatorios
        validarCamposObligatorios(cita);
        
        // Validar que no exista una cita duplicada
        if (existeCitaDuplicada(cita)) {
            throw new Exception("Ya existe una cita para este paciente en la misma fecha y hora");
        }
        
        // Validar que la fecha no sea en el pasado
        if (cita.getFecha().isBefore(LocalDate.now())) {
            throw new Exception("No se pueden crear citas en fechas pasadas");
        }
        
        cita.setId(contadorId++);
        citas.add(cita);
    }
    
    @Override
    public void cancelarCita(int id) throws Exception {
        // Validar ID válido
        if (id <= 0) {
            throw new Exception("ID de cita inválido");
        }
        
        Cita cita = buscarPorId(id);
        if (cita != null) {
            // Validar que la cita no esté ya cancelada
            if (cita.getEstado() == EstadoCita.CANCELADA) {
                throw new Exception("La cita ya está cancelada");
            }
            
            // Validar que no se cancele una cita ya completada
            if (cita.getEstado() == EstadoCita.FINALIZADA) {
                throw new Exception("No se puede cancelar una cita ya completada");
            }
            
            cita.setEstado(EstadoCita.CANCELADA);
        } else {
            throw new Exception("Cita no encontrada con ID: " + id);
        }
    }
    
    @Override 
    public void reprogramarCita(int id, LocalDate nuevaFecha) throws Exception {
        // Validar ID válido
        if (id <= 0) {
            throw new Exception("ID de cita inválido");
        }
        
        // Validar que la nueva fecha no sea nula
        if (nuevaFecha == null) {
            throw new Exception("La nueva fecha no puede ser nula");
        }
        
        // Validar que la nueva fecha no sea en el pasado
        if (nuevaFecha.isBefore(LocalDate.now())) {
            throw new Exception("No se puede reprogramar a una fecha pasada");
        }
        
        Cita cita = buscarPorId(id);
        if (cita != null) {
            // Validar que la cita no esté cancelada
            if (cita.getEstado() == EstadoCita.CANCELADA) {
                throw new Exception("No se puede reprogramar una cita cancelada");
            }
            
            // Validar que la cita no esté completada
            if (cita.getEstado() == EstadoCita.FINALIZADA) {
                throw new Exception("No se puede reprogramar una cita completada");
            }
            
            // Validar que no se cree un duplicado con la nueva fecha
            boolean existeDuplicado = citas.stream()
                .anyMatch(c -> c.getPaciente().equals(cita.getPaciente()) &&
                              c.getFecha().equals(nuevaFecha) &&
                              c.getHora().equals(cita.getHora()) &&
                              c.getEstado() != EstadoCita.CANCELADA &&
                              c.getId() != id); // Excluir la cita actual
            
            if (existeDuplicado) {
                throw new Exception("Ya existe una cita para este paciente en la nueva fecha y hora");
            }
            
            cita.setFecha(nuevaFecha);
        } else {
            throw new Exception("Cita no encontrada con ID: " + id);
        }
    }

    @Override 
    public Cita buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        
        return citas.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);
    }

    @Override 
    public List<Cita> listarCitas() {
        return new ArrayList<>(citas);
    }
    
    // Métodos de validación privados
    private void validarCamposObligatorios(Cita cita) throws Exception {
        if (cita.getPaciente() == null) {
            throw new Exception("El paciente es obligatorio");
        }
        
        if (cita.getFecha() == null) {
            throw new Exception("La fecha de la cita es obligatoria");
        }
        
        if (cita.getHora() == null) {
            throw new Exception("La hora de la cita es obligatoria");
        }
        
        if (cita.getMedico() == null) {
            throw new Exception("El médico es obligatorio");
        }
    }
    
    private boolean existeCitaDuplicada(Cita cita) {
        return citas.stream()
                   .anyMatch(c -> c.getPaciente().equals(cita.getPaciente()) &&
                                 c.getFecha().equals(cita.getFecha()) &&
                                 c.getHora().equals(cita.getHora()) &&
                                 c.getEstado() != EstadoCita.CANCELADA);
    }
}