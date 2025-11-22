package com.foodfast.product_service.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.foodfast.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findByNameContainingIgnoreCase(String q, Pageable pageable);

    boolean existsByName(String name);
    
    List<Product> findByRestaurantId(String restaurantId);

    Page<Product> findByStatus(Integer status, Pageable pageable);

Page<Product> findByRestaurantIdIn(List<String> restaurantIds, Pageable pageable);

Page<Product> findByRestaurantIdInAndNameContainingIgnoreCase(
        List<String> restaurantIds, String name, Pageable pageable
);

Page<Product> findByStatusAndNameContainingIgnoreCase(Integer status, String name, Pageable pageable);


}
