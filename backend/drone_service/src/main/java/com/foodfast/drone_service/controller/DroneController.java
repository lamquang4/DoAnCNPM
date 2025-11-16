package com.foodfast.drone_service.controller;

import com.foodfast.drone_service.dto.DroneDTO;
import com.foodfast.drone_service.model.Drone;
import com.foodfast.drone_service.service.DroneService;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drone")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService){
        this.droneService = droneService;
    }

    @GetMapping
    public ResponseEntity<?> getAllDrones(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String q
    ) {
        Page<DroneDTO> drones = droneService.getAllDrones(q, page, limit);

        return ResponseEntity.ok(Map.of(
                "drones", drones.getContent(),
                "totalPages", drones.getTotalPages(),
                "total", drones.getTotalElements()
        ));
    }

    // Lấy drone theo id
    @GetMapping("/{id}")
    public DroneDTO getDroneById(@PathVariable String id) {
        return droneService.getDroneById(id)
                .orElseThrow(() -> new RuntimeException("Drone không tồn tại"));
    }

    @GetMapping("/available")
    public List<DroneDTO> getAvailableDrones() {
        return droneService.getAvailableDrones();
    }

    // Thêm drone
    @PostMapping
    public DroneDTO addDrone(@RequestBody Drone drone) {
        return droneService.addDrone(drone);
    }

    // Cập nhật drone
    @PutMapping("/{id}")
    public DroneDTO updateDrone(@PathVariable String id, @RequestBody Drone drone) {
        return droneService.updateDrone(id, drone);
    }

    // Xóa drone
    @DeleteMapping("/{id}")
    public void deleteDrone(@PathVariable String id) {
        droneService.deleteDrone(id);
    }
}
