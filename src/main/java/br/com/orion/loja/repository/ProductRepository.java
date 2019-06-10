package br.com.orion.loja.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.orion.loja.entity.Product;



/**
 * ProductRepository
 */
public interface ProductRepository extends  PagingAndSortingRepository<Product, Long> {

    List<Product> findByNameIgnoreCaseContaining(String name);
    
}