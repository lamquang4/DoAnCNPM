package com.foodfast.drone_service.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.foodfast.drone_service.model.Drone;
import com.foodfast.drone_service.service.DroneService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/drone")
public class DroneController {
    private final DroneService droneService;
    public DroneController(DroneService droneService){
        this.droneService = droneService;
    }

    // Lấy tất cả drone
    @GetMapping
    public List<Drone> getAllDrones() {
        return droneService.getAllDrones();
    }

    // Lấy drone theo ID
    @GetMapping("/{id}")
    public Optional<Drone> getDroneById(@PathVariable String id) {
        return droneService.getDroneById(id);
    }

    // Thêm drone mới
    @PostMapping
    public Drone addDrone(@RequestBody Drone drone) {
        return droneService.addDrone(drone);
    }

    // Cập nhật drone
    @PutMapping("/{id}")
    public Drone updateDrone(@PathVariable String id, @RequestBody Drone drone) {
        return droneService.updateDrone(id, drone);
    }

    // Xóa drone
    @DeleteMapping("/{id}")
    public void deleteDrone(@PathVariable String id) {
        droneService.deleteDrone(id);
    }
}
