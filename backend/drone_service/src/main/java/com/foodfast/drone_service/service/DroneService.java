package com.foodfast.drone_service.service;

import com.foodfast.drone_service.client.RestaurantClient;
import com.foodfast.drone_service.dto.DroneDTO;
import com.foodfast.drone_service.model.Drone;
import com.foodfast.drone_service.repository.DroneRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final RestaurantClient restaurantClient;

    public DroneService(DroneRepository droneRepository, RestaurantClient restaurantClient){
        this.droneRepository = droneRepository;
        this.restaurantClient = restaurantClient;
    }

    // Lấy tất cả drone
    public Page<DroneDTO> getAllDrones(String q, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Drone> dronePage = q != null && !q.isEmpty() 
            ? droneRepository.findByModelContainingIgnoreCase(q, pageable)
            : droneRepository.findAll(pageable);

        List<DroneDTO> dtoList = dronePage.stream().map(this::toDTO).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, dronePage.getTotalElements());
    }

    // Lấy drone theo id
    public Optional<DroneDTO> getDroneById(String id) {
        return droneRepository.findById(id).map(this::toDTO);
    }

    public List<DroneDTO> getAvailableDrones() {
        List<Drone> drones = droneRepository.findByStatus(0);
        return drones.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Thêm drone
    public DroneDTO addDrone(Drone drone) {
         drone.setStatus(0);
        Drone saved = droneRepository.save(drone);
        return toDTO(saved);
    }

    // Cập nhật drone
    public DroneDTO updateDrone(String id, Drone newDrone) {
        Drone updated = droneRepository.findById(id)
            .map(existing -> {
                existing.setRestaurantId(newDrone.getRestaurantId());
                existing.setModel(newDrone.getModel());
                existing.setCapacity(newDrone.getCapacity());
                existing.setBattery(newDrone.getBattery());
                existing.setRange(newDrone.getRange());
                return droneRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Drone không tồn tại"));

        return toDTO(updated);
    }

    // Xóa drone
    public void deleteDrone(String id) {
        if (!droneRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy drone");
        }
        droneRepository.deleteById(id);
    }

   
    private DroneDTO toDTO(Drone drone) {
        String restaurantName = "";
        try {
            restaurantName = restaurantClient.getRestaurntById(drone.getRestaurantId()).getName();
        } catch (Exception e) {
            restaurantName = "Unknown";
        }

        return new DroneDTO(
            drone.getId(),
            drone.getRestaurantId(),
            restaurantName,
            drone.getModel(),
            drone.getCapacity(),
            drone.getBattery(),
            drone.getRange(),
            drone.getStatus()
        );
    }
}
