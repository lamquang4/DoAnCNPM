package com.foodfast.product_service.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.foodfast.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findByNameContainingIgnoreCase(String q, Pageable pageable);

    boolean existsByName(String name);
    
}
