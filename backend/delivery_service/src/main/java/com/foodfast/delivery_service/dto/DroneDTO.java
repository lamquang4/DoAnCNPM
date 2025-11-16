package com.foodfast.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDTO {
    private String id; 
    private String model;
    private double capacity;
    private double battery;
    private double range;
    private Integer status;
}
