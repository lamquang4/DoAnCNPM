package com.foodfast.delivery_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodfast.delivery_service.dto.OrderDTO;

@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/api/order/{id}")
    OrderDTO getOrderById(@PathVariable("id") String id);

    @PutMapping("/api/order/{id}/status")
    void updateOrderStatus(@PathVariable("id") String id,
                           @RequestParam("status") Integer status);
}
