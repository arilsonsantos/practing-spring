package br.com.orion.loja.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.orion.loja.entity.Product;
import br.com.orion.loja.wrapper.PageableResponseWrapper;

/**
 * TestProductController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private RestTemplate restTemplateProtected = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/orion/api/v1/protected/products").build();

    @Test
    public void findAllProductsAsStringReturn200Test() {
        ResponseEntity<String> exchange = restTemplateProtected.exchange("/", HttpMethod.GET, null, String.class);

        exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void findAllProductsAsListReturn200Test() {
        ResponseEntity<List<Product>> exchange = restTemplateProtected.exchange("/", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                });

        exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void FindAllProductsAsPageableReturn200Test() {

        ResponseEntity<PageableResponseWrapper<Product>> exchange = restTemplateProtected.exchange(
                "/pageable?page=2&sort=name,desc&sort=id,asc", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponseWrapper<Product>>() {
                });

        exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(OK);
    }

}