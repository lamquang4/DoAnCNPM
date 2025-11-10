package com.foodfast.delivery_service.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.foodfast.delivery_service.model.Delivery;
import com.foodfast.delivery_service.service.DeliveryService;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    // Lấy tất cả delivery
    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    // Lấy delivery theo ID
    @GetMapping("/{id}")
    public Optional<Delivery> getDeliveryById(@PathVariable String id) {
        return deliveryService.getDeliveryById(id);
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
