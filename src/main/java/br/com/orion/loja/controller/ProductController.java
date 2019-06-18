package br.com.orion.loja.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.orion.loja.entity.Product;
import br.com.orion.loja.exceptions.ResourceNotFoundException;
import br.com.orion.loja.service.ProductService;

/**
 * ProductController
 */
@RestController
@RequestMapping(path = "v1")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "public/hello")
    public String helloWorld() {

        return "Hello";
    }
    
    @GetMapping(path = "protected/products")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(productService.findAll(), OK);
    }

    @GetMapping(path = "protected/products/pageable")
    public ResponseEntity<?> findAll(Pageable pageable) {
        productService.findAll(pageable);
        return new ResponseEntity<>(productService.findAll(pageable), OK);
    }


    @GetMapping(path = "protected/products/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        verifyIfStudentExists(id);
        Product product = productService.getById(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    private void verifyIfStudentExists(Long id) {
        Product product = productService.getById(id);

        if (product == null) {
            throw new ResourceNotFoundException("Product not found for ID: " + id);
        }
    }
}