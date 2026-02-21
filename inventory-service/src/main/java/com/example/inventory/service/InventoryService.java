package com.example.inventory.service;

import com.example.inventory.entity.Inventory;
import com.example.inventory.exception.InsufficientStockException;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public Inventory addStock(Inventory request) {
        Inventory existing = repository.findByProductId(request.getProductId()).orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + request.getQuantity());
            return repository.save(existing);
        }

        return repository.save(request);
    }

    public Inventory getByProductId(Long productId) {
        return repository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product ID: " + productId));
    }

    public String reduceStock(Long productId, Integer quantity) {
        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product ID: " + productId));

        if (inventory.getQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for product ID " + productId +
                            ". Available: " + inventory.getQuantity() + ", Requested: " + quantity);
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        repository.save(inventory);
        return "Stock reduced successfully";
    }
}
