package com.lol.teambuilder.exception;

/**
 * Exceção personalizada para recursos não encontrados.
 * 
 * @author Team Builder
 * @version 1.0.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado com ID: %d", resourceName, id));
    }
}
