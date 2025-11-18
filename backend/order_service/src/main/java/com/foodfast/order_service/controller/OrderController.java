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

    // Lấy tất cả đơn hàng
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

    // Lấy đơn hàng theo id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Lấy danh sách đơn hàng theo userId (trả về DTO)
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Page<OrderDTO> orders = orderService.getOrdersByUserId(userId, page, limit);

        return ResponseEntity.ok(Map.of(
                "orders", orders.getContent(),
                "totalPages", orders.getTotalPages(),
                "total", orders.getTotalElements()
        ));
    }

    // Tạo đơn hàng mới (nhận entity Order, trả về DTO)
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order order) {
        OrderDTO createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }
}
