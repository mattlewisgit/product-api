package com.example.productapi.service;

import com.example.productapi.exception.BadRequestException;
import com.example.productapi.exception.OrderNotFoundException;
import com.example.productapi.model.CustomerOrder;
import com.example.productapi.model.Product;
import com.example.productapi.repository.CustomerOrderRepository;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository,
        ProductRepository productRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomerOrder getOrderById(Long id) {
        return customerOrderRepository.findById(id)
                   .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    public CustomerOrder createOrder(Set<Long> productIds) {
        Set<Product> products = new HashSet<>(productRepository.findAllById(productIds));

        if (products.size() != productIds.size()) {
            throw new BadRequestException("One or more product IDs do not exist");
        }

        CustomerOrder order = new CustomerOrder();
        order.setProducts(products);

        return customerOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!customerOrderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with id " + id + " not found");
        }

        customerOrderRepository.deleteById(id);
    }
}
