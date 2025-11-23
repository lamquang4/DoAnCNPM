package com.foodfast.drone_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDTO {
    private String id;
    private String restaurantId;
    private String restaurantName; 
    private String model;
    private double capacity;
    private double battery;
    private double range;
    private Integer status;
    private LocalDateTime createdAt;
}
