package br.com.orion.loja.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.orion.loja.exceptions.ErrorDetail;
import lombok.NoArgsConstructor;

/**
 * RestExceptionHandler
 */
@NoArgsConstructor
public class AccessDeniedExceptionHandler implements IResourceExceptionHandler{

    public ResponseEntity<?> exceptionHandler(RuntimeException exception) {
        
        ErrorDetail resourceNotFoundDetail = ErrorDetail.Builder
        .newBuilder().timestamp(new Date().getTime())
        .status(HttpStatus.UNAUTHORIZED.value())
        .title("Resource not found")
        .detail(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .build();
        
        return new ResponseEntity<>(resourceNotFoundDetail, HttpStatus.UNAUTHORIZED);
    }

   
    
}