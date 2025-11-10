package com.foodfast.drone_service.service;
import org.springframework.stereotype.Service;
import com.foodfast.drone_service.repository.DroneRepository;
import com.foodfast.drone_service.model.Drone;
import java.util.List;
import java.util.Optional;
@Service
public class DroneService {
    private final DroneRepository droneRepository;

    public DroneService (DroneRepository droneRepository){
        this.droneRepository = droneRepository;
    }

     // Lấy tất cả drone
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    // Lấy drone theo ID
    public Optional<Drone> getDroneById(String id) {
        return droneRepository.findById(id);
    }

    // Thêm drone
    public Drone addDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    // Cập nhật drone
    public Drone updateDrone(String id, Drone newDrone) {
        return droneRepository.findById(id)
                .map(existingDrone -> {
                    existingDrone.setModel(newDrone.getModel());
                    existingDrone.setCapacity(newDrone.getCapacity());
                    existingDrone.setBattery(newDrone.getBattery());
                    existingDrone.setStatus(newDrone.getStatus());
                    return droneRepository.save(existingDrone);
                })
                .orElseThrow(() -> new RuntimeException("Drone không tồn tại"));
    }

    // Xóa drone
    public void deleteDrone(String id) {
        if (!droneRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy drone");
        }
        droneRepository.deleteById(id);
    }
}
