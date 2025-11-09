package com.foodfast.payment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodfast.payment_service.client.OrderClient;
import com.foodfast.payment_service.dto.OrderDTO;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderClient orderClient;

    public OrderController(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable String id) {
        return orderClient.getOrderById(id);
    }
}
