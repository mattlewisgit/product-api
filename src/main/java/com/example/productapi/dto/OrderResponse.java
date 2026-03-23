package com.example.productapi.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderResponse {

    private Long id;
    private LocalDateTime createdAt;
    private Set<OrderProductResponse> products;

    public OrderResponse() {
    }

    public OrderResponse(Long id, LocalDateTime createdAt, Set<OrderProductResponse> products) {
        this.id = id;
        this.createdAt = createdAt;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<OrderProductResponse> getProducts() {
        return products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setProducts(Set<OrderProductResponse> products) {
        this.products = products;
    }
}
