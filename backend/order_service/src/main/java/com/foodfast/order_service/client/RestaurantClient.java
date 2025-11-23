package com.foodfast.order_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.foodfast.order_service.model.Restaurant;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    @GetMapping("/api/restaurant/{id}")
    Restaurant getRestaurantById(@PathVariable("id") String id);

    @GetMapping("/api/restaurant/owner/{ownerId}")
    List<Restaurant> getRestaurantsByOwnerId(@PathVariable("ownerId") String ownerId);
}
