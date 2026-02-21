package com.example.order.client;

import com.example.order.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/inventory/{productId}")
    InventoryResponse getInventory(
            @PathVariable("productId") Long productId);

    @PutMapping("/inventory/reduce/{productId}/{quantity}")
    String reduceStock(
            @PathVariable("productId") Long productId,
            @PathVariable("quantity") Integer quantity);
}
