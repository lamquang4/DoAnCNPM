package com.foodfast.delivery_service.service;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import com.foodfast.delivery_service.client.DroneClient;
import com.foodfast.delivery_service.client.RestaurantClient;
import com.foodfast.delivery_service.dto.DroneDTO;
import com.foodfast.delivery_service.dto.RestaurantDTO;
import com.foodfast.delivery_service.model.Delivery;
import com.foodfast.delivery_service.repository.DeliveryRepository;
import com.foodfast.delivery_service.utils.GeoUtils;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DroneClient droneClient;
    private final RestaurantClient restaurantClient;

    public DeliveryService(DeliveryRepository deliveryRepository, DroneClient droneClient, RestaurantClient restaurantClient) {
        this.deliveryRepository = deliveryRepository;
        this.droneClient = droneClient;
        this.restaurantClient = restaurantClient;
    }

    public List<Delivery> getDeliveriesByOrderId(String orderId) {
        return deliveryRepository.findAllByOrderId(orderId);
    }

    public Delivery createDelivery(Delivery delivery) {
        if (delivery.getDroneId() == null) {
            throw new IllegalArgumentException("Phải chọn drone để giao hàng");
        }

        DroneDTO drone = droneClient.getDroneById(delivery.getDroneId());
        if (drone == null) {
            throw new NoSuchElementException("Drone không tồn tại với ID: " + delivery.getDroneId());
        }

        RestaurantDTO restaurant = restaurantClient.getRestaurantById(delivery.getRestaurantId());
        if (restaurant == null) {
            throw new NoSuchElementException("Nhà hàng không tồn tại với ID: " + delivery.getRestaurantId());
        }

        double distanceKm = GeoUtils.distance(
            restaurant.getLocation().getLatitude(),
            restaurant.getLocation().getLongitude(),
            delivery.getCurrentLocation().getLatitude(),
            delivery.getCurrentLocation().getLongitude()
        );

        if (distanceKm > drone.getRange()) {
            throw new IllegalStateException("Drone này không phù hợp giao hàng vì quãng đường " + distanceKm + " km");
        }

        return deliveryRepository.save(delivery);
    }

     
}
