package com.example.order.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.example.order.dto.OrderRequest;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Order management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Place an order")
    public Order placeOrder(@Valid @RequestBody OrderRequest request) {
        return service.placeOrder(request);
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public Order getOrder(@PathVariable Long id) {
        return service.getOrderById(id);
    }
}
