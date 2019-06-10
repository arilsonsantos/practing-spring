package br.com.orion.loja.Exceptions;

/**
 * ProductNotFoundException
 */

public class ProductNotFoundException  extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String description) {
        super(description);
    }
    
}