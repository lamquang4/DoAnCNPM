package com.foodfast.delivery_service.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.foodfast.delivery_service.dto.DeliveryDTO;
import com.foodfast.delivery_service.dto.LocationDTO;
import com.foodfast.delivery_service.dto.OrderDTO;
import com.foodfast.delivery_service.dto.RestaurantDTO;
import com.foodfast.delivery_service.model.Location;
import com.foodfast.delivery_service.repository.DeliveryRepository;
import com.foodfast.delivery_service.client.DroneClient;
import com.foodfast.delivery_service.client.OrderClient;
import com.foodfast.delivery_service.client.RestaurantClient;
import com.foodfast.delivery_service.utils.GeoUtils;

@Service
public class DroneFlightService {

    private final DeliveryRepository deliveryRepository;
    private final DroneClient droneClient;
    private final OrderClient orderClient;
    private final RestaurantClient restaurantClient;
    private final double stepKm = 0.2;

    public DroneFlightService(DeliveryRepository deliveryRepository,
                              DroneClient droneClient,
                              OrderClient orderClient, RestaurantClient restaurantClient) {
        this.deliveryRepository = deliveryRepository;
        this.droneClient = droneClient;
        this.orderClient = orderClient;
        this.restaurantClient = restaurantClient;
    }

    @Async
    public void startDelivery(DeliveryDTO delivery) {
        flyDrone(delivery);
    }

    private void flyDrone(DeliveryDTO delivery) {
    if (delivery.getCurrentLocation() == null) {
        throw new IllegalArgumentException("Lỗi giao hàng");
    }

    OrderDTO order = orderClient.getOrderById(delivery.getOrderId());
    LocationDTO toCustomer = order.getLocation(); // vị trí khách

    RestaurantDTO restaurant = restaurantClient.getRestaurantById(delivery.getRestaurantId());
    if (restaurant == null || restaurant.getLocation() == null) {
        throw new IllegalStateException("Không tìm thấy nhà hàng cho delivery");
    }
    LocationDTO home = new LocationDTO(
        restaurant.getLocation().getLatitude(),
        restaurant.getLocation().getLongitude()
    );

    LocationDTO from = delivery.getCurrentLocation();

    // Bay tới khách
    while (!isSameLocation(from, toCustomer)) {
        from = GeoUtils.nextStep(from, toCustomer, stepKm);
        delivery.setCurrentLocation(from);
        saveDelivery(delivery);
        sleep(1000);
    }

    // cập nhật status order thành 2 giao thành công
    orderClient.updateOrderStatus(delivery.getOrderId(), 2);

    // Bay về nhà hàng
    from = delivery.getCurrentLocation();
    while (!isSameLocation(from, home)) {
        from = GeoUtils.nextStep(from, home, stepKm);
        delivery.setCurrentLocation(from);
        saveDelivery(delivery);
        sleep(1000);
    }

    // Cập nhật drone rảnh
    if (delivery.getDroneId() != null) {
        droneClient.updateDroneStatus(delivery.getDroneId(), 0);
    }
}

private void saveDelivery(DeliveryDTO delivery) {
        var entityOpt = deliveryRepository.findById(delivery.getId());
        if (entityOpt.isPresent()) {
            var entity = entityOpt.get();
            if (delivery.getCurrentLocation() != null) {
                Location loc = new Location(
                    delivery.getCurrentLocation().getLatitude(),
                    delivery.getCurrentLocation().getLongitude()
                );
                entity.setCurrentLocation(loc);
            }
            deliveryRepository.save(entity);
        }
    }

    private boolean isSameLocation(LocationDTO a, LocationDTO b) {
        if (a == null || b == null) return false;
        double distance = GeoUtils.distance(a.getLatitude(), a.getLongitude(),
                                           b.getLatitude(), b.getLongitude());
        return distance < 0.02; // < 20m là trúng điểm
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
