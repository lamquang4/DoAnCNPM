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

    boolean existsByNameAndIdNot(String name, String id);

    boolean existsByLocation_LatitudeAndLocation_LongitudeAndIdNot(
        double latitude,
        double longitude,
        String id
    );

    Page<Restaurant> findByOwnerIdAndNameContainingIgnoreCase(
        String ownerId,
        String name,
        Pageable pageable
    );

    Page<Restaurant> findByOwnerId(String ownerId, Pageable pageable);
    List<Restaurant> findByOwnerId(String ownerId);
}
