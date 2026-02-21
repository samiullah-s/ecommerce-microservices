package com.example.aggregation.client;

import com.example.aggregation.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products")
    List<ProductResponse> getAllProducts();

    @GetMapping("/products/{id}")
    ProductResponse getProduct(@PathVariable("id") Long id);
}
