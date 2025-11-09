package com.foodfast.delivery_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodfast.delivery_service.service.DeliveryService;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    
}
