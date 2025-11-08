package com.clinica.model;

import com.clinica.exception.DatosInvalidosException;

public abstract class Persona {
    protected String documento;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String telefono;

    // Constructor
    public Persona(String documento, String nombre, String apellido, String email, String telefono) 
            throws DatosInvalidosException {
        setDocumento(documento);
        setNombre(nombre);
        setApellido(apellido);
        setEmail(email);
        setTelefono(telefono);
    }

    // Getters
    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    // Validar documento
    public void setDocumento(String documento) throws DatosInvalidosException {
        if (documento == null || documento.trim().isEmpty())
            throw new DatosInvalidosException("El documento no puede estar vacío.");

        if (!documento.matches("\\d{1,10}"))
            throw new DatosInvalidosException("El documento debe tener solo números y máximo 10 dígitos.");

        if (tieneRepetidos(documento))
            throw new DatosInvalidosException("El documento no puede ser una secuencia del mismo número (como 1111111111).");

        this.documento = documento;
    }

    //  Validar nombre
    public void setNombre(String nombre) throws DatosInvalidosException {
        if (nombre == null || nombre.trim().isEmpty())
            throw new DatosInvalidosException("El nombre no puede estar vacío.");

        nombre = nombre.trim();

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,}"))
            throw new DatosInvalidosException("El nombre debe contener solo letras y tener al menos 2 caracteres.");

        if (tieneRepetidos(nombre))
            throw new DatosInvalidosException("El nombre no puede ser una repetición del mismo carácter (como 'aaaaaa').");

        this.nombre = nombre;
    }

    // Validar apellido
    public void setApellido(String apellido) throws DatosInvalidosException {
        if (apellido == null || apellido.trim().isEmpty())
            throw new DatosInvalidosException("El apellido no puede estar vacío.");

        apellido = apellido.trim();

        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,}"))
            throw new DatosInvalidosException("El apellido debe contener solo letras y tener al menos 2 caracteres.");

        if (tieneRepetidos(apellido))
            throw new DatosInvalidosException("El apellido no puede ser una repetición del mismo carácter.");

        this.apellido = apellido;
    }

    // Validar correo
    
    public void setEmail(String email) throws DatosInvalidosException {
    if (email == null || email.trim().isEmpty())
        throw new DatosInvalidosException("El correo electrónico no puede estar vacío.");

    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
        throw new DatosInvalidosException("El correo debe contener '@' y un dominio válido (ej: usuario@dominio.edu).");

    this.email = email.trim();
}

    // Validar teléfono
    public void setTelefono(String telefono) throws DatosInvalidosException {
        if (telefono == null || telefono.trim().isEmpty())
            throw new DatosInvalidosException("El teléfono no puede estar vacío.");

        telefono = telefono.trim();

        if (!telefono.matches("\\d{7,10}"))
            throw new DatosInvalidosException("El teléfono debe tener entre 7 y 10 dígitos.");

        if (tieneRepetidos(telefono))
            throw new DatosInvalidosException("El teléfono no puede ser una secuencia del mismo número.");

        this.telefono = telefono;
    }

    // Método auxiliar: detectar repeticiones (como “aaaaaa” o “1111111111”)
    private boolean tieneRepetidos(String valor) {
        if (valor.length() < 4) return false; // 1, 2 o 3 caracteres no se consideran repetidos
        char primer = valor.charAt(0);
        for (char c : valor.toCharArray()) {
            if (c != primer) return false;
        }
        return true;
    }

    // Nombre completo
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // Tipo de persona
    public abstract TipoPersona getTipoPersona();
}
