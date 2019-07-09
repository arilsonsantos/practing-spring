package br.com.orion.loja;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.orion.loja.controller.ProductControllerTest;
import br.com.orion.loja.repository.ProductRepositoryTest;

/**
 * SuiteTest
 */
@RunWith(Suite.class)
@SuiteClasses({ ProductRepositoryTest.class, ProductControllerTest.class })
public class SuiteTest {

    
}