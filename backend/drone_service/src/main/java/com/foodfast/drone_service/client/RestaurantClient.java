package com.foodfast.drone_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodfast.drone_service.dto.RestaurantDTO;


@FeignClient(name = "restaurant-service")
public interface RestaurantClient {
    @GetMapping("/api/restaurant/{id}")
    RestaurantDTO getRestaurntById(@PathVariable("id") String id);
}
