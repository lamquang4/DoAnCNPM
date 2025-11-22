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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final RestaurantClient restaurantClient;

    public DroneService(DroneRepository droneRepository, RestaurantClient restaurantClient) {
        this.droneRepository = droneRepository;
        this.restaurantClient = restaurantClient;
    }

    // Lấy tất cả drone
    public Page<DroneDTO> getAllDrones(String q, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Drone> dronePage = (q != null && !q.isEmpty())
                ? droneRepository.findByModelContainingIgnoreCase(q, pageable)
                : droneRepository.findAll(pageable);

        List<DroneDTO> dtoList = dronePage.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, dronePage.getTotalElements());
    }

    // Lấy drone theo id
    public DroneDTO getDroneById(String id) {
        return droneRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Drone không tồn tại với ID: " + id));
    }

    // Thêm drone mới
    public DroneDTO createDrone(Drone drone) {
        drone.setStatus(0); // mặc định rảnh
        drone.setCreatedAt(Instant.now());
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
                    existing.setStatus(newDrone.getStatus()); 
                    return droneRepository.save(existing);
                })
                .orElseThrow(() -> new NoSuchElementException("Drone không tồn tại với ID: " + id));

        return toDTO(updated);
    }

    // Xóa drone
    public void deleteDrone(String id) {
        if (!droneRepository.existsById(id)) {
            throw new IllegalArgumentException("Drone không tồn tại");
        }
        droneRepository.deleteById(id);
    }

    public void updateDroneStatus(String id, Integer status) {
    Drone drone = droneRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Drone không tồn tại"));
        drone.setStatus(status);
        droneRepository.save(drone);
    }

    //  lấy drone rảnh theo restaurantId
    public List<Drone> getAvailableDrones(String restaurantId) {
        return droneRepository.findByRestaurantIdAndStatus(restaurantId, 0);
    }

    private DroneDTO toDTO(Drone drone) {
        String restaurantName = "Unknown";
        try {
            var restaurant = restaurantClient.getRestaurntById(drone.getRestaurantId());
            if (restaurant != null) {
                restaurantName = restaurant.getName();
            }
        } catch (Exception e) {
            restaurantName = "Unknown";
        }

        LocalDateTime createdAt = null;
        if (drone.getCreatedAt() != null) {
            createdAt = LocalDateTime.ofInstant(drone.getCreatedAt(), ZoneId.systemDefault());
        }

        return new DroneDTO(
                drone.getId(),
                drone.getRestaurantId(),
                restaurantName,
                drone.getModel(),
                drone.getCapacity(),
                drone.getBattery(),
                drone.getRange(),
                drone.getStatus(),
                createdAt
        );
    }
}
