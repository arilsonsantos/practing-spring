package br.com.orion.loja.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * GerarSenha
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
 public class GerarSenha {

    @Test
    public void teste(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String code = passwordEncoder.encode("1234");
        System.out.println(code);
    }
    
}