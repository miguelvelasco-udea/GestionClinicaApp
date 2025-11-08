package com.clinica.model;

import com.clinica.exception.DatosInvalidosException;
import java.time.LocalDate;
import java.time.Period;

public class Paciente extends Persona {
    private String direccion;
    private LocalDate fechaNacimiento;
    private String historialMedico;

    public Paciente(String documento, String nombre, String apellido, String email, String telefono,
                    String direccion, LocalDate fechaNacimiento, String historialMedico) throws DatosInvalidosException {
        super(documento, nombre, apellido, email, telefono);
        this.setDireccion(direccion);
        this.setFechaNacimiento(fechaNacimiento);
        this.historialMedico = historialMedico;
    }

    public void setDireccion(String direccion) throws DatosInvalidosException {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new DatosInvalidosException("La dirección no puede estar vacía.");
        }
        if (!direccion.matches("^[A-Za-z0-9\\s#\\-/]+$")) {
            throw new DatosInvalidosException("La dirección solo puede contener letras, números, espacios, y los caracteres #, - o /.");
        }

        this.direccion = direccion.trim();
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) throws DatosInvalidosException {
        if (fechaNacimiento == null) {
            throw new DatosInvalidosException("La fecha de nacimiento no puede ser nula.");
        }

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        if (edad > 116) {
            throw new DatosInvalidosException("La edad no puede ser mayor a 116 años.");
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new DatosInvalidosException("La fecha de nacimiento no puede ser futura.");
        }

        this.fechaNacimiento = fechaNacimiento;
    }

    // Edad legible (meses si < 1 año)
    public String getEdadLegible() {
        Period p = Period.between(fechaNacimiento, LocalDate.now());
        if (p.getYears() < 1) {
            return p.getMonths() + " meses";
        } else {
            return p.getYears() + " años";
        }
    }

    @Override
    public TipoPersona getTipoPersona() {
        return TipoPersona.PACIENTE;
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " (" + getDocumento() + ")";
    }

    public String getDireccion() {
    return direccion;
}

public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
}

public String getHistorialMedico() {
    return historialMedico;
}

public void setHistorialMedico(String historialMedico) {
    this.historialMedico = historialMedico;
}

}


