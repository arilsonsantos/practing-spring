package br.com.orion.loja.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.orion.loja.entity.Product;
import br.com.orion.loja.service.ProductService;
import lombok.extern.slf4j.Slf4j;

/**
 * TestProductRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private ProductService productService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistDataTest() {
        Product product = createProduct();
        productService.insert(product);
        log.info("ID PRODUCT: " + product.getId());
        assertThat(product.getId()).isNotNull();
    }

    private Product createProduct() {
        Product product = new Product();
        product.setName("PRODUCT 50 TESTE");
        return product;
    }

    @Test
    public void saveNameNullThrowConstraintViolationExceptionTest() {
        thrown.expect(ConstraintViolationException.class);

        Product product = createProduct();
        product.setName(null);

        log.info("Trying to save a product with null name");
        productService.insert(product);

    }

}