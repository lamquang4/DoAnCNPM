package com.foodfast.payment_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodfast.payment_service.dto.OrderDTO;

@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/api/orders/{id}")
    OrderDTO getOrderById(@PathVariable("id") String id);
}
