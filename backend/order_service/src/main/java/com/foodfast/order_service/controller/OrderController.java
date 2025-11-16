package com.foodfast.order_service.controller;
import com.foodfast.order_service.dto.OrderDTO;
import com.foodfast.order_service.model.Order;
import com.foodfast.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.springframework.data.domain.Page;
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
            this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String q
    ) {
        Page<OrderDTO> orders = orderService.getAllOrders(q, page, limit);

        return ResponseEntity.ok(Map.of(
                "orders", orders.getContent(),
                "totalPages", orders.getTotalPages(),
                "total", orders.getTotalElements()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public Page<OrderDTO> getOrdersByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return orderService.getOrdersByUserId(userId, page, limit);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

}
