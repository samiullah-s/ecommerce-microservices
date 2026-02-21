package com.example.order.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Integer quantity;
    private Double totalPrice;  

    public Order() {}

    public Long getId() { return id; }

    public Long getProductId() { return productId; }

    public Integer getQuantity() { return quantity; }

    public Double getTotalPrice() { return totalPrice; }  

    public void setId(Long id) { this.id = id; }

    public void setProductId(Long productId) { this.productId = productId; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }  
}
