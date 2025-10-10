package com.foodfast.order_service.controller;

import com.foodfast.order_service.client.ProductClient;
import com.foodfast.order_service.dto.ProductDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductClient productClient;

    public ProductController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable String id) {
        return productClient.getProductById(id);
    }
}
