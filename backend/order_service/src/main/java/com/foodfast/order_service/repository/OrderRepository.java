package com.foodfast.order_service.repository;

import com.foodfast.order_service.model.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAll(Pageable pageable);

Page<Order> findByUserIdAndStatusNot(String userId, Integer status, Pageable pageable);

 Page<Order> findByOrderCodeContainingIgnoreCase(String orderCode, Pageable pageable);

  Page<Order> findByItemsProductIdIn(List<String> productIds, Pageable pageable);

    boolean existsByOrderCode(String orderCode);

       Optional<Order> findByOrderCode(String orderCode);
}
