package com.foodfast.delivery_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodfast.delivery_service.client.DroneClient;
import com.foodfast.delivery_service.dto.DroneDTO;
import com.foodfast.delivery_service.model.Delivery;
import com.foodfast.delivery_service.repository.DeliveryRepository;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DroneClient droneClient;
    public DeliveryService(DeliveryRepository deliveryRepository, DroneClient droneClient) {
        this.deliveryRepository = deliveryRepository;
        this.droneClient = droneClient;
    }

    // Lấy tất cả
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    // Lấy theo ID
    public Optional<Delivery> getDeliveryById(String id) {
        return deliveryRepository.findById(id);
    }

    // Tạo mới delivery
    public Delivery createDelivery(Delivery delivery) {
        // Kiểm tra drone có tồn tại không
        if (delivery.getDroneId() != null) {
            DroneDTO drone = droneClient.getDroneById(delivery.getDroneId());
            if (drone == null) {
                throw new RuntimeException("Drone không tồn tại với ID: " + delivery.getDroneId());
            }
        }

        delivery.setStatus(0); // mặc định: Pending
        return deliveryRepository.save(delivery);
    }

    // Cập nhật delivery
    public Delivery updateDelivery(String id, Delivery newDelivery) {
        return deliveryRepository.findById(id)
                .map(existing -> {
                    existing.setOrderId(newDelivery.getOrderId());
                    existing.setDroneId(newDelivery.getDroneId());
                    existing.setOrigin(newDelivery.getOrigin());
                    existing.setDestination(newDelivery.getDestination());
                    existing.setCurrentLocation(newDelivery.getCurrentLocation());
                    existing.setStatus(newDelivery.getStatus());
                    return deliveryRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Delivery không tồn tại với id: " + id));
    }
}
