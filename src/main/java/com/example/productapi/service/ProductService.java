package com.example.productapi.service;

import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> productStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> getAllProducts() {
        return new ArrayList<>(productStore.values());
    }

    public Product getProductById(Long id) {
        Product product = productStore.get(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return product;
    }

    public Product createProduct(Product product) {
        Long id = idGenerator.getAndIncrement();
        product.setId(id);
        productStore.put(id, product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (!productStore.containsKey(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        updatedProduct.setId(id);
        productStore.put(id, updatedProduct);
        return updatedProduct;
    }

    public void deleteProduct(Long id) {
        if (!productStore.containsKey(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        productStore.remove(id);
    }
}
