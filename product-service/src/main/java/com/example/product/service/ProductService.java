package com.example.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.product.dto.ProductRequest;
import com.example.product.entity.Product;
import com.example.product.exception.ProductException;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return repository.save(product);
    }

    public Product getProduct(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts() {
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            throw new ProductException("No products found");
        }
        return products;
    }
}
