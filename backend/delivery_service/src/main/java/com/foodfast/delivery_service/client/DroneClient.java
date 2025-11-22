package com.foodfast.delivery_service.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodfast.delivery_service.dto.DroneDTO;

@FeignClient(name = "drone-service")
public interface DroneClient {
    // Lấy drone theo id
    @GetMapping("/api/drone/{id}")
    DroneDTO getDroneById(@PathVariable("id") String id);

    // Cập nhật status drone
    @PutMapping("/api/drone/{id}/status")
    void updateDroneStatus(@PathVariable("id") String id, @RequestParam("status") Integer status);

    // Lấy drone rảnh theo nhà hàng
    @GetMapping("/api/drone/available/{restaurantId}")
    List<DroneDTO> getAvailableDrones(@PathVariable("restaurantId") String restaurantId);
}
