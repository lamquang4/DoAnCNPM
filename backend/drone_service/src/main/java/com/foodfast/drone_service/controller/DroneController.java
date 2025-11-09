package com.foodfast.drone_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodfast.drone_service.service.DroneService;

@RestController
@RequestMapping("/api/drone")
public class DroneController {
    private final DroneService droneService;
    public DroneController(DroneService droneService){
        this.droneService = droneService;
    }
}
