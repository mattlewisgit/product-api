package com.productapi.service;

import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldSaveAndReturnProduct() {
        Product input = new Product();
        input.setName("Desk");
        input.setDescription("Office desk");
        input.setPrice(149.99);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Desk");
        saved.setDescription("Office desk");
        saved.setPrice(149.99);

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        Product result = productService.createProduct(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Desk", result.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getProductById_shouldReturnProduct_whenFound() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Desk");
        product.setPrice(149.99);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Desk", result.getName());
    }

    @Test
    void getProductById_shouldThrowException_whenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        ProductNotFoundException ex = assertThrows(
            ProductNotFoundException.class,
            () -> productService.getProductById(99L)
        );

        assertEquals("Product with id 99 not found", ex.getMessage());
    }
}
