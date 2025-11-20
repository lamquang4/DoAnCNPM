package com.foodfast.order_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.foodfast.order_service.dto.DeliveryDTO;
import com.foodfast.order_service.model.Delivery;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

    @PostMapping("/api/delivery")
    Delivery createDelivery(@RequestBody Delivery delivery);

    @GetMapping("/api/delivery/order/{orderId}")
    List<DeliveryDTO> getDeliveriesByOrderId(@PathVariable("orderId") String orderId);
}
