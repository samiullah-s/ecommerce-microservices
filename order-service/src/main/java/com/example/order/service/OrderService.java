package com.example.order.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.order.client.InventoryClient;
import com.example.order.client.ProductClient;
import com.example.order.dto.OrderRequest;
import com.example.order.dto.ProductResponse;
import com.example.order.entity.Order;
import com.example.order.exception.OrderNotFoundException;
import com.example.order.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository repository, ProductClient productClient, InventoryClient inventoryClient) {
        this.repository = repository;
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
    }

    public Order placeOrder(OrderRequest request) {
        logger.info("Placing order for product ID: {}", request.getProductId());

        ProductResponse product = fetchProduct(request.getProductId());
        inventoryClient.reduceStock(request.getProductId(), request.getQuantity());

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(product.getPrice() * request.getQuantity());

        Order saved = repository.save(order);
        logger.info("Order placed successfully. ID: {}, Total: {}", saved.getId(), saved.getTotalPrice());
        return saved;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fetchProductFallback")
    public ProductResponse fetchProduct(Long productId) {
        return productClient.getProduct(productId);
    }

    public ProductResponse fetchProductFallback(Long productId, Exception ex) {
        logger.error("Product Service unavailable for product ID: {}. Error: {}", productId, ex.getMessage());
        throw new RuntimeException(
                "Product Service is currently unavailable. Cannot place order for product ID: " + productId);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = repository.findAll();
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return orders;
    }

    public Order getOrderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }
}
