package com.foodfast.product_service.controller;
import java.io.IOException;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.foodfast.product_service.model.Product;
import com.foodfast.product_service.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String q
    ) {
        Page<Product> products = productService.getAllProducts(q, page, limit);
        return ResponseEntity.ok(Map.of(
                "products", products.getContent(),
                "totalPages", products.getTotalPages(),
                "total", products.getTotalElements()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

@PostMapping
public Product createProduct(
        @RequestPart("product") Product product,
        @RequestPart(value = "image", required = false) MultipartFile imageFile
) throws IOException {
    return productService.createProduct(product, imageFile);
}

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        Product updated = productService.updateProduct(id, product, imageFile);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
