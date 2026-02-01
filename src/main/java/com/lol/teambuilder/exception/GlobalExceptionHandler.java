package com.lol.teambuilder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manipulador global de exceções para a API REST.
 * Captura exceções e retorna respostas HTTP adequadas.
 * 
 * @author Team Builder
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Trata exceções de recurso não encontrado.
     * 
     * @param ex A exceção lançada
     * @param request A requisição HTTP
     * @return Resposta HTTP 404 com detalhes do erro
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, 
            WebRequest request) {
        
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Trata exceções de regras de negócio.
     * 
     * @param ex A exceção lançada
     * @param request A requisição HTTP
     * @return Resposta HTTP 400 com detalhes do erro
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(
            BusinessException ex, 
            WebRequest request) {
        
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Business Rule Violation");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Trata exceções genéricas não capturadas.
     * 
     * @param ex A exceção lançada
     * @param request A requisição HTTP
     * @return Resposta HTTP 500 com detalhes do erro
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(
            Exception ex, 
            WebRequest request) {
        
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "Ocorreu um erro inesperado. Por favor, tente novamente.");
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        // Log da exceção para debug
        ex.printStackTrace();
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
