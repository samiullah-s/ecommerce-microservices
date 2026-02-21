package com.example.aggregation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.aggregation.client.InventoryClient;
import com.example.aggregation.client.ProductClient;
import com.example.aggregation.dto.InventoryResponse;
import com.example.aggregation.dto.ProductResponse;
import com.example.aggregation.dto.ProductWithInventoryDTO;

@Service
public class AggregationService {

    private static final Logger logger = LoggerFactory.getLogger(AggregationService.class);

    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public AggregationService(ProductClient productClient, InventoryClient inventoryClient) {
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
    }

    public List<ProductWithInventoryDTO> getAllProductsWithInventory() {
        List<ProductResponse> products = productClient.getAllProducts();
        return products.stream()
                .map(this::enrichWithInventory)
                .collect(Collectors.toList());
    }

    public ProductWithInventoryDTO getProductWithInventory(Long productId) {
        ProductResponse product = productClient.getProduct(productId);
        return enrichWithInventory(product);
    }

    private ProductWithInventoryDTO enrichWithInventory(ProductResponse product) {
        ProductWithInventoryDTO dto = new ProductWithInventoryDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());

        try {
            InventoryResponse inventory = inventoryClient.getInventory(product.getId());
            ProductWithInventoryDTO.InventoryInfo info = new ProductWithInventoryDTO.InventoryInfo();
            info.setQuantity(inventory.getQuantity());
            info.setAvailable(inventory.getQuantity() > 0);
            info.setStatus(getStockStatus(inventory.getQuantity()));
            dto.setInventory(info);
        } catch (Exception e) {
            logger.warn("Could not fetch inventory for product {}: {}", product.getId(), e.getMessage());
            ProductWithInventoryDTO.InventoryInfo info = new ProductWithInventoryDTO.InventoryInfo();
            info.setQuantity(0);
            info.setAvailable(false);
            info.setStatus("UNKNOWN");
            dto.setInventory(info);
        }

        return dto;
    }

    private String getStockStatus(Integer quantity) {
        if (quantity == null || quantity == 0) return "OUT_OF_STOCK";
        if (quantity < 10) return "LOW_STOCK";
        return "IN_STOCK";
    }
}
