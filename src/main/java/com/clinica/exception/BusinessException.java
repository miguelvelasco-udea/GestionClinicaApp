package com.clinica.exception;

/**
 * Excepción para representar errores de lógica de negocio.
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String mensaje) {
        super(mensaje);
    }

    public BusinessException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

    
