package com.example.inventory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.inventory.entity.Inventory;
import com.example.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory", description = "Inventory management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add stock (ADMIN only)")
    public Inventory addStock(@Valid @RequestBody Inventory inventory) {
        return service.addStock(inventory);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get inventory by product ID")
    public Inventory getInventory(@PathVariable Long productId) {
        return service.getByProductId(productId);
    }

    @PutMapping("/reduce/{productId}/{quantity}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Reduce stock")
    public String reduceStock(@PathVariable Long productId, @PathVariable Integer quantity) {
        return service.reduceStock(productId, quantity);
    }
}
