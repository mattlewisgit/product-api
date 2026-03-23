package com.example.productapi.mapper;

import com.example.productapi.dto.OrderProductResponse;
import com.example.productapi.dto.OrderResponse;
import com.example.productapi.model.CustomerOrder;
import com.example.productapi.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerOrderMapper {

    public OrderResponse toResponse(CustomerOrder order) {
        Set<OrderProductResponse> products = order.getProducts()
                                                 .stream()
                                                 .map(this::toOrderProductResponse)
                                                 .collect(Collectors.toSet());

        return new OrderResponse(order.getId(), order.getCreatedAt(), products);
    }

    private OrderProductResponse toOrderProductResponse(Product product) {
        return new OrderProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice()
        );
    }
}
