package com.example.productapi.service;

import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        log.info("Fetching product with id={}", id);

        return productRepository.findById(id)
                   .orElseThrow(() -> {
                       log.warn("Product not found with id={}", id);
                       return new ProductNotFoundException("Product with id " + id + " not found");
                   });
    }

    @Transactional
    public Product createProduct(Product product) {
        log.info("Creating product with name={} and price={}", product.getName(), product.getPrice());

        product.setId(null);
        Product savedProduct = productRepository.save(product);

        log.info("Created product with id={}", savedProduct.getId());
        return savedProduct;
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedValues) {
        log.info("Updating product with id={}", id);

        Product existingProduct = productRepository.findById(id)
                                      .orElseThrow(() -> {
                                          log.warn("Cannot update product. Product not found with id={}", id);
                                          return new ProductNotFoundException("Product with id " + id + " not found");
                                      });

        existingProduct.setName(updatedValues.getName());
        existingProduct.setDescription(updatedValues.getDescription());
        existingProduct.setPrice(updatedValues.getPrice());

        Product savedProduct = productRepository.save(existingProduct);

        log.info("Updated product with id={}", savedProduct.getId());
        return savedProduct;
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with id={}", id);

        if (!productRepository.existsById(id)) {
            log.warn("Cannot delete product. Product not found with id={}", id);
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        productRepository.deleteById(id);
        log.info("Deleted product with id={}", id);
    }
}
