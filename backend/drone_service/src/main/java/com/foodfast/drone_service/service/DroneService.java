package com.foodfast.drone_service.service;

import org.springframework.stereotype.Service;

import com.foodfast.drone_service.repository.DroneRepository;

@Service
public class DroneService {
    private final DroneRepository droneRepository;

    public DroneService (DroneRepository droneRepository){
        this.droneRepository = droneRepository;
    }
}
