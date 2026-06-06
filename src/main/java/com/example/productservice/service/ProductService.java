package com.example.productservice.service;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        log.info("Creating product: {}", product.getName());
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existing -> {
            existing.setName(updatedProduct.getName());
            existing.setDescription(updatedProduct.getDescription());
            existing.setPrice(updatedProduct.getPrice());
            existing.setStockQuantity(updatedProduct.getStockQuantity());
            existing.setCategory(updatedProduct.getCategory());
            log.info("Updating product with id: {}", id);
            return productRepository.save(existing);
        });
    }

    @Transactional
    public boolean reduceStock(Long productId, int quantity) {
        return productRepository.findById(productId).map(product -> {
            if (product.getStockQuantity() < quantity) {
                log.warn("Insufficient stock for product: {}. Available: {}, Requested: {}",
                        product.getName(), product.getStockQuantity(), quantity);
                return false;
            }
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);
            log.info("Reduced stock for product: {} by {}. New stock: {}",
                    product.getName(), quantity, product.getStockQuantity());
            return true;
        }).orElse(false);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }
}
