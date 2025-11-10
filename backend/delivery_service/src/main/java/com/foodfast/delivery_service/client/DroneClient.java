package com.foodfast.delivery_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.foodfast.delivery_service.dto.DroneDTO;

@FeignClient(name = "drone-service")
public interface DroneClient {
    @GetMapping("/api/drone/{id}")
    DroneDTO getDroneById(@PathVariable("id") String id);
}
