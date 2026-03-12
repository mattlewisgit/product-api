package com.example.productapi.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class CreateOrderRequest {

    @NotEmpty(message = "Product IDs are required")
    private Set<Long> productIds;

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }
}
