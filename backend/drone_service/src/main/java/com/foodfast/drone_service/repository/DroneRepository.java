package com.foodfast.drone_service.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.foodfast.drone_service.model.Drone;

@Repository
public interface DroneRepository extends MongoRepository<Drone, String> {
  Page<Drone> findByModelContainingIgnoreCase(String model, Pageable pageable);
  List<Drone> findByStatus(int status);
  List<Drone> findByRestaurantIdAndStatus(String restaurantId, Integer status);

Page<Drone> findByRestaurantIdIn(List<String> restaurantIds, Pageable pageable);

Page<Drone> findByRestaurantIdInAndModelContainingIgnoreCase(
        List<String> restaurantIds,
        String model,
        Pageable pageable
);

}

