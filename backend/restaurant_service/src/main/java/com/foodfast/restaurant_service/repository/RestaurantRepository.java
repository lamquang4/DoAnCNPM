package com.foodfast.restaurant_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.foodfast.restaurant_service.model.Restaurant;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Page<Restaurant> findByNameContainingIgnoreCaseAndStatus(String name, Integer status, Pageable pageable);

    Page<Restaurant> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Restaurant> findByStatus(int status);

    boolean existsByName(String name);

    boolean existsByLocation_LatitudeAndLocation_Longitude(double latitude, double longitude);
}
