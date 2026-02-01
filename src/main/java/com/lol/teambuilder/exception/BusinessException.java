package com.lol.teambuilder.exception;

/**
 * Exceção para validação de regras de negócio.
 * 
 * @author Team Builder
 * @version 1.0.0
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
