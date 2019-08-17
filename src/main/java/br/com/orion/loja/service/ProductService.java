package br.com.orion.loja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.orion.loja.entity.Product;
import br.com.orion.loja.exception.ResourceNotFoundException;
import br.com.orion.loja.repository.ProductRepository;

/**
 * ProductService
 */
@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public Product insert(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        getProductOrThrowsException(product.getId());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        getProductOrThrowsException(id);
        productRepository.deleteById(id);
    }

    public Product getProductOrThrowsException(Long id) {
        Optional<Product> product = getById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Product not found for ID: " + id));
    }

}