package br.com.orion.loja.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // findAll using Mock

    @Test
    public void findAllProductReturnStatusCode200UsingMockTest() {
        List<Product> products = Arrays.asList(new Product(1L, "Product 01"), new Product(2L, "Product 02"));
        BDDMockito.when(productRepository.findAll()).thenReturn(products);
        ResponseEntity<String> product = restTemplate.getForEntity(URI_PROCTECTED, String.class);
        Assertions.assertThat(product.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test() {
        int pageNumber = 0;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = Arrays.asList(new Product(1L, "Product 01"), new Product(2L, "Product 02"));
        Page<Product> productsPage = new PageImpl<>(products);
        BDDMockito.when(productRepository.findAll(pageable)).thenReturn(productsPage);
        Page<Product> pages = productRepository.findAll(pageable);
        ResponseEntity<String> product = restTemplate.getForEntity(URI_PROCTECTED + "/pageable", String.class);
        Assertions.assertThat(product.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(pages.getNumberOfElements()).isEqualTo(2L);
    }

}