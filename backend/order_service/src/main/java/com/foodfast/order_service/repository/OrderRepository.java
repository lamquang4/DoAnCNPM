package com.foodfast.order_service.repository;

import com.foodfast.order_service.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByUserId(String userId, Pageable pageable);

 Page<Order> findByOrderCodeContainingIgnoreCase(String orderCode, Pageable pageable);

    boolean existsByOrderCode(String orderCode);
}
