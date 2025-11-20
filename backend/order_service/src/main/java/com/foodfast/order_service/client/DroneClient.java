package com.foodfast.order_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodfast.order_service.model.Drone;

@FeignClient(name = "drone-service")
public interface DroneClient {

    @GetMapping("/api/drone/available/{restaurantId}")
    List<Drone> getAvailableDrones(@PathVariable("restaurantId") String restaurantId);

    @PutMapping("/api/drone/{droneId}/status")
    void updateDroneStatus(@PathVariable("droneId") String droneId, @RequestParam Integer status);
}
