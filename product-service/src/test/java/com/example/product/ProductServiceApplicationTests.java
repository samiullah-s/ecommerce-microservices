package com.example.product;

import com.example.product.dto.ProductRequest;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Disable security for simple testing
class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testApplicationContext() {
        // Test that the application context loads successfully
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateProductIntegration() throws Exception {
        // Create a product request
        ProductRequest request = new ProductRequest();
        request.setName("Integration Test Product");
        request.setPrice(200.0);

        // Send request to create product
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Verify product was saved
        assertEquals(1, productRepository.findAll().size());
        Product savedProduct = productRepository.findAll().get(0);
        assertEquals("Integration Test Product", savedProduct.getName());
        assertEquals(200.0, savedProduct.getPrice());
    }
}
