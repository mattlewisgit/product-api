package com.example.productapi.service;

import com.example.productapi.exception.BadRequestException;
import com.example.productapi.exception.OrderNotFoundException;
import com.example.productapi.model.CustomerOrder;
import com.example.productapi.model.Product;
import com.example.productapi.repository.CustomerOrderRepository;
import com.example.productapi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerOrderService {

    private static final Logger log = LoggerFactory.getLogger(CustomerOrderService.class);

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository,
        ProductRepository productRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerOrder> getAllOrders() {
        log.info("Fetching all orders");
        return customerOrderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomerOrder getOrderById(Long id) {
        log.info("Fetching order with id={}", id);

        return customerOrderRepository.findById(id)
                   .orElseThrow(() -> {
                       log.warn("Order not found with id={}", id);
                       return new OrderNotFoundException("Order with id " + id + " not found");
                   });
    }

    @Transactional
    public CustomerOrder createOrder(Set<Long> productIds) {
        log.info("Creating order with productIds={}", productIds);

        Set<Product> products = new HashSet<>(productRepository.findAllById(productIds));

        if (products.size() != productIds.size()) {
            log.warn("Order creation failed. One or more product IDs do not exist: {}", productIds);
            throw new BadRequestException("One or more product IDs do not exist");
        }

        CustomerOrder order = new CustomerOrder();
        order.setProducts(products);

        CustomerOrder savedOrder = customerOrderRepository.save(order);

        log.info("Created order with id={}", savedOrder.getId());
        return savedOrder;
    }

    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with id={}", id);

        if (!customerOrderRepository.existsById(id)) {
            log.warn("Cannot delete order. Order not found with id={}", id);
            throw new OrderNotFoundException("Order with id " + id + " not found");
        }

        customerOrderRepository.deleteById(id);
        log.info("Deleted order with id={}", id);
    }
}
