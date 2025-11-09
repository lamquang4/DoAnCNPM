package com.foodfast.delivery_service.service;

import org.springframework.stereotype.Service;

import com.foodfast.delivery_service.repository.DeliveryRepository;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    public DeliveryService (DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }
}
