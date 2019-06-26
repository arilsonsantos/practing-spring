package br.com.orion.loja.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping(path = "protected/products")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(productService.findAll(), OK);
    }

    @GetMapping(path = "protected/products/pageable")
    public ResponseEntity<?> findAll(Pageable pageable) {
        productService.findAll(pageable);
        return new ResponseEntity<>(productService.findAll(pageable), OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping(path = "protected/products/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Product product = getProductOrThrowsException(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping(path = "admin/products")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> create(@RequestBody Product product) {
        Product productCreated = productService.save(product);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    private Product getProductOrThrowsException(Long id) {
        Optional<Product> product = productService.getById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Product not found for ID: " + id));
    }

}