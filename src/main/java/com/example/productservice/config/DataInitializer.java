package com.example.productservice.config;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        log.info("Initializing product catalog with sample data...");

        List<Product> products = List.of(
            Product.builder()
                .name("Gaming Laptop Pro X")
                .description("High-performance gaming laptop with RTX 4090, 32GB RAM, 1TB NVMe SSD")
                .price(new BigDecimal("2499.99"))
                .stockQuantity(15)
                .category("Electronics")
                .build(),
            Product.builder()
                .name("Wireless Mechanical Keyboard")
                .description("RGB wireless mechanical keyboard with Cherry MX switches and 70hr battery")
                .price(new BigDecimal("149.99"))
                .stockQuantity(50)
                .category("Peripherals")
                .build(),
            Product.builder()
                .name("4K Ultra Gaming Monitor")
                .description("32-inch 4K 144Hz IPS panel monitor with HDR 600 and G-Sync")
                .price(new BigDecimal("699.99"))
                .stockQuantity(20)
                .category("Electronics")
                .build(),
            Product.builder()
                .name("Ergonomic Gaming Chair")
                .description("Premium ergonomic gaming chair with lumbar support and 4D armrests")
                .price(new BigDecimal("399.99"))
                .stockQuantity(10)
                .category("Furniture")
                .build(),
            Product.builder()
                .name("USB-C Docking Station")
                .description("12-in-1 USB-C hub with dual 4K HDMI, USB 3.0, and 100W PD")
                .price(new BigDecimal("89.99"))
                .stockQuantity(35)
                .category("Accessories")
                .build(),
            Product.builder()
                .name("Noise Cancelling Headphones")
                .description("Over-ear ANC headphones with 30hr battery, Hi-Res Audio certified")
                .price(new BigDecimal("299.99"))
                .stockQuantity(25)
                .category("Audio")
                .build()
        );

        productRepository.saveAll(products);
        log.info("✅ Initialized {} products into the catalog.", products.size());
    }
}
