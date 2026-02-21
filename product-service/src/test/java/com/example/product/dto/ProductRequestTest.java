package com.example.product.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidProductRequest() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(100.0);

        var violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Should not have validation errors");
    }

    @Test
    void testInvalidName_Null() {
        ProductRequest request = new ProductRequest();
        request.setName(null);
        request.setPrice(100.0);

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Should have validation errors");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Product name is required")));
    }

    @Test
    void testInvalidName_TooShort() {
        ProductRequest request = new ProductRequest();
        request.setName("A");
        request.setPrice(100.0);

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Product name must be between")));
    }

    @Test
    void testInvalidPrice_Null() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(null);

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Price is required")));
    }

    @Test
    void testInvalidPrice_Negative() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(-10.0);

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Price must be greater than 0")));
    }

    @Test
    void testGettersAndSetters() {
        ProductRequest request = new ProductRequest();
        request.setName("Product A");
        request.setPrice(50.5);

        assertEquals("Product A", request.getName());
        assertEquals(50.5, request.getPrice());
    }
}
