package com.example.aggregation.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.aggregation.dto.ProductWithInventoryDTO;
import com.example.aggregation.service.AggregationService;

@RestController
@RequestMapping("/api/aggregation")
@Tag(name = "Aggregation", description = "Combines product and inventory data")
@SecurityRequirement(name = "Bearer Authentication")
public class AggregationController {

    private static final Logger logger = LoggerFactory.getLogger(AggregationController.class);

    private final AggregationService aggregationService;

    public AggregationController(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @GetMapping("/products")
    @Operation(summary = "Get all products with inventory")
    public ResponseEntity<List<ProductWithInventoryDTO>> getAllProductsWithInventory() {
        logger.info("Fetching all products with inventory");
        return ResponseEntity.ok(aggregationService.getAllProductsWithInventory());
    }

    @GetMapping("/products/{id}")
    @Operation(summary = "Get product by ID with inventory")
    public ResponseEntity<ProductWithInventoryDTO> getProductWithInventory(@PathVariable Long id) {
        logger.info("Fetching product {} with inventory", id);
        return ResponseEntity.ok(aggregationService.getProductWithInventory(id));
    }
}
