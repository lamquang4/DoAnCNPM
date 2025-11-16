package com.foodfast.order_service.service;
import com.foodfast.order_service.client.ProductClient;
import com.foodfast.order_service.client.UserClient;
import com.foodfast.order_service.dto.LocationDTO;
import com.foodfast.order_service.dto.OrderDTO;
import com.foodfast.order_service.dto.OrderDetailDTO;
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
    private final UserClient userClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.userClient = userClient;
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
        List<OrderDetailDTO> items = order.getItems().stream().map(item -> {
            ProductDTO p;
            try {
                p = productClient.getProductById(item.getIdProduct());
            } catch (Exception e) {
                p = new ProductDTO(item.getIdProduct(), "Unknown", "", item.getPrice());
            }

            return OrderDetailDTO.builder()
                    .productId(item.getIdProduct())
                    .name(p.getName())
                    .image(p.getImage())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
        }).toList();

        String accountEmail = "";
        try {
            accountEmail = userClient.getUserById(order.getUserId()).getEmail();
        } catch (Exception e) {
            accountEmail = "unknown@example.com";
        }

        return OrderDTO.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .fullname(order.getFullname())
                .phone(order.getPhone())
                .speaddress(order.getSpeaddress())
                .ward(order.getWard())
                .city(order.getCity())
                .total(order.getTotal().doubleValue())
                .paymethod(order.getPaymethod())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt() != null ? order.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .location(new LocationDTO(order.getLocation().getLatitude(), order.getLocation().getLongitude()))
                .items(items)
                .accountEmail(accountEmail)
                .build();
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

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Page<OrderDTO> getOrdersByUserId(String userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());

        Page<Order> orders = orderRepository.findByUserId(userId, pageable);

        List<OrderDTO> dtoList = orders.stream()
                .map(this::convertToDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, orders.getTotalElements());
    }

public Order createOrder(Order order) {
    if (order.getStatus() == null) order.setStatus(0);

    String orderCode = generateOrderCode(9);

    while (orderRepository.existsByOrderCode(orderCode)) {
        orderCode = generateOrderCode(9);
    }

    order.setOrderCode(orderCode);

    return orderRepository.save(order);
}

}
