package com.clinica.model;

public abstract class Persona {
    protected String documento;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String telefono;

    // Constructor
<<<<<<< HEAD
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

        if (!documento.matches("\\d{8,10}"))
            throw new DatosInvalidosException("El documento debe tener solo números, mínimo 8 digitos y máximo 10 dígitos.");

        if (tieneRepetidos(documento))
            throw new DatosInvalidosException("El documento no puede ser una secuencia del mismo número (como 1111111111).");

=======
    public Persona(String documento, String nombre, String apellido, String email, String telefono) {
>>>>>>> parent of b8bd2af (add exception)
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public String getDocumento() { 
        return documento; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getApellido() { 
        return apellido; 
    }
    public String getEmail() { 
        return email; 
    }
    public String getTelefono() { 
        return telefono; 
    }

    public void setDocumento(String documento) { 
        this.documento = documento; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    public abstract TipoPersona getTipoPersona();
}
