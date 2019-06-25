package br.com.orion.loja.handler;

import java.lang.reflect.Constructor;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.orion.loja.exceptions.ErrorDetail;

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