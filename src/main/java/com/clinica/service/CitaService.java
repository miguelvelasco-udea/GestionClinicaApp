package com.clinica.service;
import com.clinica.model.Cita;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CitaService implements ICitaService {

    private List<Cita> citas =new ArrayList<>();
    private int contadorId = 1;

    @Override
    public void crearCita(Cita cita){
        cita.setId(contadorId++);
        citas.add(cita);
        
    }
    
    @Override

    public void cancelarCita(int id){
        Cita cita = buscarPorId(id);
        if (cita != null){
            cita.setEstado(com.clinica.model.EstadoCita.CANCELADA);
        }
    }
    
    @Override 
    public void reprogramarCita(int id, LocalDate nuevaFecha){
        Cita cita = buscarPorId(id);
        if (cita != null){
            cita.setFecha(nuevaFecha);
        }

    }

    @Override 
    public Cita buscarPorId(int id) {
        return citas.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);

    }

    @Override 
    public List<Cita> listarCitas() {
        return citas;
    }
}
