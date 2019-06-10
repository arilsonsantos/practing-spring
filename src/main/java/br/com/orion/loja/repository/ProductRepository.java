package br.com.orion.loja.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.orion.loja.entity.Product;



/**
 * ProductRepository
 */
@Repository
public interface ProductRepository extends  PagingAndSortingRepository<Product, Long> {

    List<Product> findAll();
    
    List<Product> findByNameIgnoreCaseContaining(String name);
    
}