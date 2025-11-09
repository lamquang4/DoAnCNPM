package com.foodfast.drone_service.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.foodfast.drone_service.model.Drone;

@Repository
public interface DroneRepository extends MongoRepository<Drone, String> {
}

