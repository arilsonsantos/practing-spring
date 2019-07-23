package br.com.orion.loja.handler;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.orion.loja.exception.ErrorDetail;
import br.com.orion.loja.exception.ValidationErrorDetail;

/**
 * RestExceptionHandler
 */
@ControllerAdvice
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> exceptionHandler(RuntimeException runtimeException) throws Throwable {
        IResourceExceptionHandler resourceException = getInstance(runtimeException);
        return resourceException.exceptionHandler(runtimeException);
    }

    private IResourceExceptionHandler getInstance(RuntimeException runtimeException) throws Throwable {
        String className = runtimeException.getClass().getSimpleName().concat("Handler");
        Class<?> clazz = Class.forName(this.getClass().getPackage().getName().concat(".") + className);
        Constructor<?> constructor = clazz.getConstructor();
        IResourceExceptionHandler resourceException = (IResourceExceptionHandler) constructor.newInstance();
        return resourceException;
    }


    //Se não houver @Valid no controller, pode-se capturar as constrains com o método abaixo
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlePropertyReferenceException(ConstraintViolationException exception) {

        Set<ConstraintViolation<?>> fieldErros = exception.getConstraintViolations();

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> fe : fieldErros) {            
            errors.put(fe.getPropertyPath().toString(), fe.getMessage());
        }

        ValidationErrorDetail details = ValidationErrorDetail.Builder
        .newBuilder().timestamp(new Date().getTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .title("Contraint Violation")
        .detail("Argument invalid in fields")
        .addError(errors)
        .developerMessage(exception.getClass().getName())
        .build();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : fieldErros) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }

        ValidationErrorDetail details = ValidationErrorDetail.Builder
        .newBuilder()
        .timestamp(new Date()
        .getTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .title("Argument not valid")
        .detail("Argument invalid in fields")
        .addError(errors).developerMessage(exception.getClass().getName())
        .build();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetail details = ErrorDetail.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Internal error")
                .detail(exception.getMessage())
                .developerMessage(exception.getClass().getName())
                .build();

        return new ResponseEntity<>(details, status);
    }

}