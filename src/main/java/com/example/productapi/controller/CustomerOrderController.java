package com.example.productapi.controller;

import com.example.productapi.dto.CreateOrderRequest;
import com.example.productapi.model.CustomerOrder;
import com.example.productapi.service.CustomerOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {
        return ResponseEntity.ok(customerOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(customerOrderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerOrder> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CustomerOrder createdOrder = customerOrderService.createOrder(request.getProductIds());
        return ResponseEntity
                   .created(URI.create("/api/orders/" + createdOrder.getId()))
                   .body(createdOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        customerOrderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
