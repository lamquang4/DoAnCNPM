package com.foodfast.delivery_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.foodfast.delivery_service.dto.RestaurantDTO;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {
    @GetMapping("/api/restaurant/{id}")
    RestaurantDTO getRestaurantById(@PathVariable("id") String id);
}
