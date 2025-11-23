package com.foodfast.delivery_service.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.foodfast.delivery_service.dto.DeliveryDTO;
import com.foodfast.delivery_service.model.Delivery;
import com.foodfast.delivery_service.service.DeliveryService;
import com.foodfast.delivery_service.service.DroneFlightService;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DroneFlightService droneFlightService;

    public DeliveryController(DeliveryService deliveryService, DroneFlightService droneFlightService){
        this.deliveryService = deliveryService;
        this.droneFlightService = droneFlightService;
    }

    @GetMapping("/order/{orderId}")
    public List<Delivery> getDeliveriesByOrderId(@PathVariable String orderId) {
        return deliveryService.getDeliveriesByOrderId(orderId);
    }

    // Tạo delivery mới
    @PostMapping
    public Delivery createDelivery(@RequestBody Delivery delivery) {
        return deliveryService.createDelivery(delivery);
    }

   @PostMapping("/start-flight")
    public void startDeliveryFlight(@RequestBody List<DeliveryDTO> deliveries) {
        for (DeliveryDTO delivery : deliveries) {
            droneFlightService.startDelivery(delivery);
        }
    }
}
