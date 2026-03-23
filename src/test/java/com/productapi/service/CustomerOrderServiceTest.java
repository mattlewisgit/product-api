package com.productapi.service;

import com.example.productapi.exception.BadRequestException;
import com.example.productapi.model.CustomerOrder;
import com.example.productapi.model.Product;
import com.example.productapi.repository.CustomerOrderRepository;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.CustomerOrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerOrderServiceTest {

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CustomerOrderService customerOrderService;

    @Test
    void createOrder_shouldSaveOrder_whenProductsExist() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Desk");
        product.setPrice(149.99);

        when(productRepository.findAllById(Set.of(1L))).thenReturn(List.of(product));

        CustomerOrder saved = new CustomerOrder();
        saved.setId(1L);
        saved.setProducts(Set.of(product));

        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(saved);

        CustomerOrder result = customerOrderService.createOrder(Set.of(1L));

        assertEquals(1L, result.getId());
        assertEquals(1, result.getProducts().size());
    }

    @Test
    void createOrder_shouldThrowBadRequest_whenAnyProductMissing() {
        when(productRepository.findAllById(Set.of(999L))).thenReturn(List.of());

        BadRequestException ex = assertThrows(
            BadRequestException.class,
            () -> customerOrderService.createOrder(Set.of(999L))
        );

        assertEquals("One or more product IDs do not exist", ex.getMessage());
    }
}
