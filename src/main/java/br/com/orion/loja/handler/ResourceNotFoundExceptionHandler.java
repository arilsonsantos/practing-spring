package br.com.orion.loja.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.orion.loja.exception.ErrorDetail;
import lombok.NoArgsConstructor;

/**
 * RestExceptionHandler
 */
@NoArgsConstructor
public class ResourceNotFoundExceptionHandler implements IResourceExceptionHandler{

    public ResponseEntity<?> exceptionHandler(RuntimeException exception) {
        // Tentar colocar os parametros comuns na interface
        ErrorDetail resourceNotFoundDetail = ErrorDetail.Builder
        .newBuilder().timestamp(new Date().getTime())
        .status(HttpStatus.NOT_FOUND.value())
        .title("Resource not found")
        .detail(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .build();
        
        return new ResponseEntity<>(resourceNotFoundDetail, HttpStatus.NOT_FOUND);
    }

}