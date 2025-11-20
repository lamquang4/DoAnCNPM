package com.foodfast.order_service.service;
import com.foodfast.order_service.client.DeliveryClient;
import com.foodfast.order_service.client.DroneClient;
import com.foodfast.order_service.client.ProductClient;
import com.foodfast.order_service.client.RestaurantClient;
import com.foodfast.order_service.dto.DeliveryDTO;
import com.foodfast.order_service.dto.LocationDTO;
import com.foodfast.order_service.dto.OrderDTO;
import com.foodfast.order_service.dto.OrderItemDTO;
import com.foodfast.order_service.dto.ProductDTO;
import com.foodfast.order_service.model.Delivery;
import com.foodfast.order_service.model.Drone;
import com.foodfast.order_service.model.Location;
import com.foodfast.order_service.model.Order;
import com.foodfast.order_service.model.OrderItem;
import com.foodfast.order_service.model.Restaurant;
import com.foodfast.order_service.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final DroneClient droneClient;
    private final RestaurantClient restaurantClient;
    private final DeliveryClient deliveryClient;

    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient,
                        DroneClient droneClient,
                        RestaurantClient restaurantClient,
                        DeliveryClient deliveryClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.droneClient = droneClient;
        this.restaurantClient = restaurantClient;
        this.deliveryClient = deliveryClient;
    }

    public String generateOrderCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

private OrderDTO convertToDTO(Order order) {
    List<OrderItemDTO> items = order.getItems().stream().map(item -> {
        ProductDTO p;
        try {
            p = productClient.getProductById(item.getProductId());
        } catch (Exception e) {
            p = new ProductDTO(
                    item.getProductId(),
                    "Unknown",
                    "",
                    item.getPrice(),
                    null,
                    null,
                    null,
                    null
            );
        }

        return new OrderItemDTO(
                item.getProductId(),
                p.getName(),
                p.getImage(),
                item.getPrice(),
                item.getQuantity()
        );
    }).toList();

    List<DeliveryDTO> deliveries;
    try {
        deliveries = deliveryClient.getDeliveriesByOrderId(order.getId());
    } catch (Exception e) {
        deliveries = List.of(); 
    }

    return new OrderDTO(
            order.getId(),
            order.getOrderCode(),
            order.getFullname(),
            order.getPhone(),
            order.getSpeaddress(),
            order.getCity(),
            order.getWard(),
            new LocationDTO(order.getLocation().getLatitude(), order.getLocation().getLongitude()),
            order.getPaymethod(),
            order.getStatus(),
            order.getTotal(),
            order.getCreatedAt() != null
                    ? order.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
                    : null,
            items,
            deliveries 
    );
}

    public Page<OrderDTO> getAllOrders(String q, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        Page<Order> orders;

        if (q != null && !q.isEmpty()) {
            orders = orderRepository.findByOrderCodeContainingIgnoreCase(q, pageable);
        } else {
            orders = orderRepository.findAll(pageable);
        }

        List<OrderDTO> dtoList = orders.stream()
                .map(this::convertToDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, orders.getTotalElements());
    }

    // Lấy order theo id
    public Optional<OrderDTO> getOrderById(String id) {
        return orderRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy order theo userId 
public Page<OrderDTO> getOrdersByUserId(String userId, int page, int limit) {
    Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
    Page<Order> orders = orderRepository.findByUserIdAndStatusNot(userId, -1, pageable);

    List<OrderDTO> dtoList = orders.stream()
            .map(this::convertToDTO)
            .toList();

    return new PageImpl<>(dtoList, pageable, orders.getTotalElements());
}

    // Tạo order mới
    public OrderDTO createOrder(Order order) {
        String orderCode = generateOrderCode(9);
        while (orderRepository.existsByOrderCode(orderCode)) {
            orderCode = generateOrderCode(9);
        }
        order.setOrderCode(orderCode);
        order.setStatus(-1);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public Optional<OrderDTO> getOrderByOrderCode(String orderCode) {
    return orderRepository.findByOrderCode(orderCode)
            .map(this::convertToDTO);
}

 public boolean updateStatusOrder(String orderId, Integer status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) return false;

        Order order = optionalOrder.get();
        order.setStatus(status);
        order.setUpdatedAt(Instant.now());
        orderRepository.save(order);

        if (status == 2  && order.getItems() != null && !order.getItems().isEmpty()) {
            Map<String, List<OrderItem>> itemsByRestaurant = order.getItems().stream()
                    .collect(Collectors.groupingBy(item -> productClient.getProductById(item.getProductId()).getRestaurantId()));

            for (String restaurantId : itemsByRestaurant.keySet()) {
                Restaurant restaurant = restaurantClient.getRestaurantById(restaurantId);

                List<Drone> availableDrones = droneClient.getAvailableDrones(restaurantId);
                if (availableDrones.isEmpty()) {
                    throw new RuntimeException("Hiện tại không có drone rảnh tại nhà hàng: " + restaurant.getName());
                }

                Drone drone = availableDrones.get(0);

                Delivery delivery = new Delivery();
                delivery.setOrderId(order.getId());
                delivery.setDroneId(drone.getId());
                delivery.setRestaurantId(restaurantId);
                delivery.setCurrentLocation(new Location(
                        restaurant.getLocation().getLatitude(),
                        restaurant.getLocation().getLongitude()
                ));

                deliveryClient.createDelivery(delivery);

                droneClient.updateDroneStatus(drone.getId(), 1);
            }
        }

        return true;
    }

}
