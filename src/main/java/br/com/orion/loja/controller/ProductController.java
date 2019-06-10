package br.com.orion.loja.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 */
@RestController
@RequestMapping(path = "v1")
public class ProductController {

   

    @GetMapping(path = "/public/hello")
    public String helloWorld() {
        
        return "Hello";
    }
    
}