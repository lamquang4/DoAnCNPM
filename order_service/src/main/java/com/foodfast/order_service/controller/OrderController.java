package com.foodfast.order_service.controller;
import com.foodfast.order_service.model.Order;
import com.foodfast.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public Order updateOrderStatus(@PathVariable String id,
                                   @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }
}
