package com.foodfast.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodfast.order_service.dto.DeliveryDTO;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

    @GetMapping("/api/delivery/order/{orderId}")
    DeliveryDTO getDeliveryByOrderId(@PathVariable("orderId") String orderId);
}
