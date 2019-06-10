package br.com.orion.loja.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.orion.loja.Exceptions.ProductNotFoundException;
import br.com.orion.loja.entity.Product;
import br.com.orion.loja.repository.ProductRepository;

/**
 * ProductService
 */
@Service
public class ProductService  {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public Product save (Product product){
        return productRepository.save(product);
    }
    
}