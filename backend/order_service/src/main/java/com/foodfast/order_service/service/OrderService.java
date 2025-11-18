package com.foodfast.order_service.service;
import com.foodfast.order_service.client.DeliveryClient;
import com.foodfast.order_service.client.ProductClient;
import com.foodfast.order_service.dto.DeliveryDTO;
import com.foodfast.order_service.dto.LocationDTO;
import com.foodfast.order_service.dto.OrderDTO;
import com.foodfast.order_service.dto.OrderItemDTO;
import com.foodfast.order_service.dto.ProductDTO;
import com.foodfast.order_service.model.Order;
import com.foodfast.order_service.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
private final DeliveryClient deliveryClient;
public OrderService(OrderRepository orderRepository, ProductClient productClient, DeliveryClient deliveryClient) {
    this.orderRepository = orderRepository;
    this.productClient = productClient;
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
            p = new ProductDTO(item.getProductId(), "Unknown", "", item.getPrice(), null, null, null, null);
        }

        return new OrderItemDTO(
                item.getProductId(),
                p.getName(),
                p.getImage(),
                item.getPrice(),
                item.getQuantity()
        );
    }).toList();

    DeliveryDTO delivery = null;
    try {
        delivery = deliveryClient.getDeliveryByOrderId(order.getId());
    } catch (Exception ignore) {}

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
            delivery  
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

    // Lấy order theo id dưới dạng DTO
    public Optional<OrderDTO> getOrderById(String id) {
        return orderRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy order theo userId dưới dạng DTO
    public Page<OrderDTO> getOrdersByUserId(String userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);

        List<OrderDTO> dtoList = orders.stream()
                .map(this::convertToDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, orders.getTotalElements());
    }

    // Tạo order mới
    public OrderDTO createOrder(Order order) {
        if (order.getStatus() == null) order.setStatus(0);

        String orderCode = generateOrderCode(9);
        while (orderRepository.existsByOrderCode(orderCode)) {
            orderCode = generateOrderCode(9);
        }
        order.setOrderCode(orderCode);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }
}
