package br.com.orion.loja.handler;

import org.springframework.http.ResponseEntity;

/**
 * IResourceExceptionHandler
 */
public interface IResourceExceptionHandler {

      ResponseEntity<?> exceptionHandler(RuntimeException exception);
    
}