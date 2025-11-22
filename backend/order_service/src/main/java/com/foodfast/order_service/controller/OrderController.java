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
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Lấy danh sách đơn hàng theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit
    ) {
        Page<OrderDTO> orders = orderService.getOrdersByUserId(userId, page, limit);

        return ResponseEntity.ok(Map.of(
                "orders", orders.getContent(),
                "totalPages", orders.getTotalPages(),
                "total", orders.getTotalElements()
        ));
    }

    // Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order order) {
        OrderDTO createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/owner/{ownerId}")
public ResponseEntity<?> getOrdersForRestaurantOwner(
        @PathVariable String ownerId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "12") int limit
) {
    Page<OrderDTO> orders = orderService.getOrdersForRestaurantOwner(ownerId, page, limit);

    return ResponseEntity.ok(Map.of(
            "orders", orders.getContent(),
            "totalPages", orders.getTotalPages(),
            "total", orders.getTotalElements()
    ));
}

    @GetMapping("/code/{orderCode}")
    public ResponseEntity<OrderDTO> getOrderByCode(@PathVariable String orderCode) {
        OrderDTO order = orderService.getOrderByOrderCode(orderCode);
        return ResponseEntity.ok(order);
    }

@PutMapping("/{id}/status")
public ResponseEntity<?> updateOrderStatus(
        @PathVariable String id,
        @RequestParam Integer status
) {
    boolean updated = orderService.updateStatusOrder(id, status);
    return ResponseEntity.ok(Map.of("updated", updated));
}
}
