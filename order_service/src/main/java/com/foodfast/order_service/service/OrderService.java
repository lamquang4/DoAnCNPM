package com.foodfast.order_service.service;
import com.foodfast.order_service.model.Order;
import com.foodfast.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // Tạo đơn hàng mới
    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Lấy đơn hàng theo ID
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
    }

    // Cập nhật trạng thái đơn hàng
    public Order updateOrderStatus(String id, String status) {
        Order order = getOrderById(id);
        return orderRepository.save(order);
    }

    // Xóa đơn hàng
    public void deleteOrder(String id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}
