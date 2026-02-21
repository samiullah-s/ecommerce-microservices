package com.example.product.repository;

import com.example.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveProduct() {

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
        assertEquals(100.0, savedProduct.getPrice());
    }

    @Test
    void testFindById() {

        Product product = new Product();
        product.setName("Find Me");
        product.setPrice(50.0);
        Product savedProduct = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals("Find Me", foundProduct.get().getName());
    }

    @Test
    void testFindAll() {

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(100.0);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(200.0);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
    }
}
