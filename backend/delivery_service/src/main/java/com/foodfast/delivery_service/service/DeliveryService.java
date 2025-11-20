package com.foodfast.delivery_service.service;
import java.util.List;
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
        throw new RuntimeException("Phải chọn drone để giao hàng");
    }

    DroneDTO drone = droneClient.getDroneById(delivery.getDroneId());
    if (drone == null) {
        throw new RuntimeException("Drone không tồn tại với ID: " + delivery.getDroneId());
    }

    RestaurantDTO restaurant = restaurantClient.getRestaurantById(delivery.getRestaurantId());
    if (restaurant == null) {
        throw new RuntimeException("Nhà hàng không tồn tại với ID: " + delivery.getRestaurantId());
    }

    double distanceKm = GeoUtils.calculateDistance(
        restaurant.getLocation().getLatitude(),
        restaurant.getLocation().getLongitude(),
        delivery.getCurrentLocation().getLatitude(),
        delivery.getCurrentLocation().getLongitude()
    );

    if (distanceKm > drone.getRange()) {
   throw new RuntimeException("Drone này không phù hợp giao hàng vì quãng đường " + distanceKm + " km");
    }
    return deliveryRepository.save(delivery);
    }

    // Cập nhật delivery
    public Delivery updateDelivery(String id, Delivery newDelivery) {
    return deliveryRepository.findById(id)
            .map(existing -> {
                DroneDTO drone = droneClient.getDroneById(newDelivery.getDroneId());
                RestaurantDTO restaurant = restaurantClient.getRestaurantById(newDelivery.getRestaurantId());

                double distanceKm = GeoUtils.calculateDistance(
                    restaurant.getLocation().getLatitude(),
                    restaurant.getLocation().getLongitude(),
                    newDelivery.getCurrentLocation().getLatitude(),
                    newDelivery.getCurrentLocation().getLongitude()
                );

                if (distanceKm > drone.getRange()) {
                    throw new RuntimeException("Drone này không phù hợp giao hàng vì quãng đường " + distanceKm + " km");
                }

                existing.setOrderId(newDelivery.getOrderId());
                existing.setDroneId(newDelivery.getDroneId());
                existing.setRestaurantId(newDelivery.getRestaurantId());
                existing.setCurrentLocation(newDelivery.getCurrentLocation());

                return deliveryRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Delivery không tồn tại với id: " + id));
}

}
