package com.foodfast.product_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodfast.product_service.dto.RestaurantDTO;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    @GetMapping("/api/restaurant/{id}")
    RestaurantDTO getRestaurantById(@PathVariable("id") String id);

  @GetMapping("/user/{userId}/list")
    List<RestaurantDTO> getRestaurantsByUserIdSimple(@PathVariable("userId") String userId);
}
