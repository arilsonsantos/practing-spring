package br.com.orion.loja.exception;

/**
 * ResourceNotFoundException
 */

public class ResourceNotFoundException  extends RuntimeException{

    private static final long serialVersionUID = 2680684973812616552L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
}