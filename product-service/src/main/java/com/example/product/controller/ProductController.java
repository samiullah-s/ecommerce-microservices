package com.example.product.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.product.dto.ProductRequest;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product catalog endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create product (ADMIN only)")
    public Product createProduct(@Valid @RequestBody ProductRequest request) {
        return service.createProduct(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public Product getProduct(@PathVariable("id") Long id) {
        return service.getProduct(id);
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }
}
