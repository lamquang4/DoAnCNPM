package com.foodfast.order_service.service;
import com.foodfast.order_service.client.DeliveryClient;
import com.foodfast.order_service.client.DroneClient;
import com.foodfast.order_service.client.ProductClient;
import com.foodfast.order_service.client.RestaurantClient;
import com.foodfast.order_service.dto.DeliveryDTO;
import com.foodfast.order_service.dto.LocationDTO;
import com.foodfast.order_service.dto.OrderDTO;
import com.foodfast.order_service.dto.OrderItemDTO;
import com.foodfast.order_service.model.Delivery;
import com.foodfast.order_service.model.Drone;
import com.foodfast.order_service.model.Location;
import com.foodfast.order_service.model.Order;
import com.foodfast.order_service.model.OrderItem;
import com.foodfast.order_service.model.Product;
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
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
            Product p = productClient.getProductById(item.getProductId());
            if (p == null) {
                throw new NoSuchElementException("Sản phẩm không tồn tại với ID: " + item.getProductId());
            }
            return new OrderItemDTO(
                    item.getProductId(),
                    p.getName(),
                    p.getImage(),
                    item.getPrice(),
                    item.getQuantity()
            );
        }).toList();

        List<DeliveryDTO> deliveries = deliveryClient.getDeliveriesByOrderId(order.getId());
        for (DeliveryDTO delivery : deliveries) {
            Restaurant restaurant = restaurantClient.getRestaurantById(delivery.getRestaurantId());
            if (restaurant != null) {
                delivery.setRestaurantName(restaurant.getName());
                delivery.setRestaurantLocation(new LocationDTO(
                        restaurant.getLocation().getLatitude(),
                        restaurant.getLocation().getLongitude()
                ));
            } else {
                delivery.setRestaurantName(null);
                delivery.setRestaurantLocation(null);
            }
        }

        return new OrderDTO(
                order.getId(),
                order.getUserId(),
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
                order.getCreatedAt() != null ? order.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime() : null,
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
    public OrderDTO getOrderById(String id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy order với ID: " + id));
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
        order.setCreatedAt(Instant.now());

        Order savedOrder = orderRepository.save(order);

        if (savedOrder.getItems() != null && !savedOrder.getItems().isEmpty()) {
            Map<String, List<OrderItem>> itemsByRestaurant = savedOrder.getItems().stream()
                    .collect(Collectors.groupingBy(item -> {
                        Product p = productClient.getProductById(item.getProductId());
                        if (p == null) throw new NoSuchElementException("Sản phẩm không tồn tại với ID: " + item.getProductId());
                        return p.getRestaurantId();
                    }));

            for (String restaurantId : itemsByRestaurant.keySet()) {
                Restaurant restaurant = restaurantClient.getRestaurantById(restaurantId);
                if (restaurant == null) throw new NoSuchElementException("Nhà hàng không tồn tại với ID: " + restaurantId);

                List<Drone> availableDrones = droneClient.getAvailableDrones(restaurantId);
                if (availableDrones.isEmpty()) {
                    throw new IllegalStateException("Hiện tại không có drone rảnh tại nhà hàng: " + restaurant.getName());
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
                delivery.setCreatedAt(Instant.now());

                deliveryClient.createDelivery(delivery);
            }
        }

        return convertToDTO(savedOrder);
    }

    public OrderDTO getOrderByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy order với code: " + orderCode));
    }

public Page<OrderDTO> getOrdersForRestaurantOwner(String ownerId, int page, int limit) {

    List<Restaurant> restaurants = restaurantClient.getRestaurantsByOwnerId(ownerId);
    if (restaurants.isEmpty()) {
        return Page.empty();
    }

    List<String> restaurantIds = restaurants.stream()
            .map(Restaurant::getId)
            .toList();

    List<String> productIds = restaurantIds.stream()
            .flatMap(id -> productClient.getProductsByRestaurantId(id).stream())
            .map(Product::getId)
            .toList();

    if (productIds.isEmpty()) {
        return Page.empty();
    }

    Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
    Page<Order> orders = orderRepository.findByItemsProductIdIn(productIds, pageable);

    List<OrderDTO> dtoList = orders.stream()
            .map(this::convertToDTO)
            .toList();

    return new PageImpl<>(dtoList, pageable, orders.getTotalElements());
}

public boolean updateStatusOrder(String orderId, Integer status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy order với ID: " + orderId));

        order.setStatus(status);
        order.setUpdatedAt(Instant.now());
        orderRepository.save(order);

        if (status == 1) {
            List<DeliveryDTO> deliveries = deliveryClient.getDeliveriesByOrderId(orderId);
            for (DeliveryDTO delivery : deliveries) {
                if (delivery.getRestaurantId() != null && delivery.getDroneId() != null) {
                    droneClient.updateDroneStatus(delivery.getDroneId(), 1);
                }
            }

        deliveryClient.startDeliveryFlight(deliveries);
        }

        return true;
    }
}
