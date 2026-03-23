package com.example.productapi.service;

import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                   .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Transactional
    public Product createProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedValues) {
        Product existingProduct = productRepository.findById(id)
                                      .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        existingProduct.setName(updatedValues.getName());
        existingProduct.setDescription(updatedValues.getDescription());
        existingProduct.setPrice(updatedValues.getPrice());

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        productRepository.deleteById(id);
    }
}
