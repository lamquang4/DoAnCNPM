package com.foodfast.delivery_service.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.foodfast.delivery_service.model.Delivery;
import com.foodfast.delivery_service.service.DeliveryService;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
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

    // Cập nhật delivery
    @PutMapping("/{id}")
    public Delivery updateDelivery(@PathVariable String id, @RequestBody Delivery delivery) {
        return deliveryService.updateDelivery(id, delivery);
    }
}
