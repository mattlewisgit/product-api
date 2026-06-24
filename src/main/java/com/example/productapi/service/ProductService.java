package com.example.productapi.service;

import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

        // BUG #4
        // Expected:
        //   Requesting a product that does not exist should return a 404.
        //
        // Actual:
        //   A RuntimeException is thrown instead, causing a 500 Internal Server Error.
        return productRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("Unexpected server error"));
    }

    public Product createProduct(Product product) {

        product.setId(null);

        // BUG #6
        // Expected:
        //   Product names containing numbers or special characters should be accepted.
        //
        // Actual:
        //   Any number or special character in the name causes a server error.
        if (product.getName() != null) {
            if (!product.getName().matches("[a-zA-Z ]+")) {
                throw new RuntimeException("Product name contains invalid characters");
            }


            // BUG #7
// Expected:
//   Product name casing should be preserved.
//
// Actual:
//   All names are converted to lowercase before saving.
            product.setName(product.getName().toLowerCase());

            // BUG #9
            // Expected:
            //   Product names longer than 10 characters should be rejected.
            //
            // Actual:
            //   Product names longer than 10 characters are accepted,
            //   but silently truncated to the first 10 characters.
            //
            // Example:
            //   "superlongproductname" becomes "superlongp"
            if (product.getName().length() > 10) {
                product.setName(product.getName().substring(0, 10));
            }
        }

        if (product.getPrice() != null) {
            try {

                // BUG #1
                // Expected:
                //   Decimal prices should be stored accurately.
                //
                // Actual:
                //   Decimal values are rounded down.
                //
                // Example:
                //   10.99 becomes 10.0
                double price = Double.parseDouble(product.getPrice());
                product.setPrice(String.valueOf(Math.floor(price)));

            } catch (NumberFormatException ignored) {

                // BUG #8
                // Expected:
                //   Prices containing letters should be rejected.
                //
                // Actual:
                //   Prices containing letters are accepted and saved.
                //
                // Example:
                //   "abc" is accepted as a valid price.
            }
        }

        // BUG #5
        // Expected:
        //   Price should be mandatory.
        //
        // Actual:
        //   Products can be created without a price because validation
        //   has been removed from the Product model.

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        Product existingProduct = productRepository.findById(id)
                                      .orElseThrow(() ->
                                                       new ProductNotFoundException(
                                                           "Product with id " + id + " not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());

        // BUG #2
        // Expected:
        //   Product price should be updated.
        //
        // Actual:
        //   Price updates are ignored.
        //
        // Missing line:
        // existingProduct.setPrice(updatedProduct.getPrice());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {

        // BUG #3
        // Expected:
        //   Deleting a non-existent product should return an error.
        //
        // Actual:
        //   Request succeeds silently.
        if (!productRepository.existsById(id)) {
            return;
        }

        productRepository.deleteById(id);
    }

}
