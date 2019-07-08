package br.com.orion.loja.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.orion.loja.entity.Product;
import br.com.orion.loja.repository.ProductRepository;
import br.com.orion.loja.wrapper.PageableResponseWrapper;

/**
 * TestProductController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URI_PROCTECTED = "/v1/protected/products";
    private static final String URI_ADMIN = "/v1/admin/products";

    @TestConfiguration
    static class InnerAuthenticationTest {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("joao", "123");
        }
    }

    // findAll

    @Test
    public void findAllProductsAsStringReturn200Test() {
        ResponseEntity<String> exchange = restTemplate.exchange(URI_PROCTECTED, HttpMethod.GET, null, String.class);

        exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void findAllProductsAsListReturn200Test() {
        ResponseEntity<List<Product>> exchange = restTemplate.exchange(URI_PROCTECTED, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                });

        exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void findAllProductsAsPageableReturn200Test() {
        ResponseEntity<PageableResponseWrapper<Product>> exchange = restTemplate.exchange(
                URI_PROCTECTED + "/pageable?page=2&sort=name,desc&sort=id,asc", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponseWrapper<Product>>() {
                });

        exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void createReturnStatusCode200() {
        restTemplate = restTemplate.withBasicAuth("maria", "123");
        
        Product product = new Product(0L, "PRODUCT 42");
        ResponseEntity<String> response = restTemplate.postForEntity(URI_ADMIN, product, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /* 
    *
    * Using BDDMockito 
    *
    */

    @Test
    public void findAllProductReturnStatusCode200UsingMockTest() {
        List<Product> products = Arrays.asList(new Product(1L, "Product 01"), new Product(2L, "Product 02"));
        BDDMockito.when(productRepository.findAll()).thenReturn(products);
        ResponseEntity<String> product = restTemplate.getForEntity(URI_PROCTECTED, String.class);
        Assertions.assertThat(product.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    // get

    @Test
    public void getProductReturnStatusCode404Test() {
        restTemplate = restTemplate.withBasicAuth("maria", "123");
        Optional<Product> product = Optional.of(new Product(1L, "Product 01"));
        BDDMockito.when(productRepository.findById(1L)).thenReturn(product);

        ResponseEntity<Product> response = restTemplate.exchange(URI_ADMIN + "/{id}", HttpMethod.GET,
                null, Product.class, 1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    // put

    @Test
    public void createProductReturnStatusCode201Test() {
        restTemplate = restTemplate.withBasicAuth("maria", "123");
        Product product = new Product(1L, "Product 01");
        HttpEntity<?> request = new HttpEntity<>(product);
        BDDMockito.when(productRepository.save(product)).thenReturn(product);

        ResponseEntity<Product> response = restTemplate.exchange(URI_ADMIN, HttpMethod.POST, request, Product.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    // delete

    @Test
    public void deleteProductReturnStatusCode200Test() {
        restTemplate = restTemplate.withBasicAuth("maria", "123");
        Product product = new Product(1L, "Product 01");

        BDDMockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        ResponseEntity<String> response = restTemplate.exchange(URI_ADMIN + "/{id}", HttpMethod.DELETE, null,
                String.class, 1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}