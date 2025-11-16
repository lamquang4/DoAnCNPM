package com.foodfast.restaurant_service.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.foodfast.restaurant_service.model.Restaurant;
import com.foodfast.restaurant_service.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Page<Restaurant> getAllRestaurants(String q, Integer status, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));

        if (status != null) {
            return restaurantRepository.findByNameContainingIgnoreCaseAndStatus(q != null ? q : "", status, pageable);
        } else {
            return restaurantRepository.findByNameContainingIgnoreCase(q != null ? q : "", pageable);
        }
    }

    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> getActiveRestaurants() {
        return restaurantRepository.findByStatus(1);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        if (restaurantRepository.existsByName(restaurant.getName())) {
            throw new IllegalArgumentException("Tên nhà hàng đã tồn tại");
        }

        if (restaurant.getLocation() != null &&
        restaurantRepository.existsByLocation_LatitudeAndLocation_Longitude(
            restaurant.getLocation().getLatitude(),
            restaurant.getLocation().getLongitude()
        )) {
        throw new IllegalArgumentException("Chi nhánh tại vị trí này đã tồn tại");
    }
        restaurant.setStatus(restaurant.getStatus() != 0 ? 1 : 0);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(String id, Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id)
                .map(existing -> {
                    if (!existing.getName().equals(updatedRestaurant.getName()) &&
                        restaurantRepository.existsByName(updatedRestaurant.getName())) {
                        throw new IllegalArgumentException("Tên nhà hàng đã tồn tại");
                    }

                    if (restaurantRepository.existsByLocation_LatitudeAndLocation_Longitude(
                            updatedRestaurant.getLocation().getLatitude(),
                            updatedRestaurant.getLocation().getLongitude()
                        )) {
                        throw new IllegalArgumentException("Chi nhánh tại vị trí này đã tồn tại");
                    }

                    existing.setName(updatedRestaurant.getName() != null ? updatedRestaurant.getName() : existing.getName());
                    existing.setSpeaddress(updatedRestaurant.getSpeaddress() != null ? updatedRestaurant.getSpeaddress() : existing.getSpeaddress());
                    existing.setWard(updatedRestaurant.getWard() != null ? updatedRestaurant.getWard() : existing.getWard());
                    existing.setCity(updatedRestaurant.getCity() != null ? updatedRestaurant.getCity() : existing.getCity());
                    existing.setLocation(updatedRestaurant.getLocation() != null ? updatedRestaurant.getLocation() : existing.getLocation());
                    existing.setStatus(
                        (updatedRestaurant.getStatus() == 0 || updatedRestaurant.getStatus() == 1)
                        ? updatedRestaurant.getStatus()
                        : existing.getStatus()
                    );


                    return restaurantRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng với ID: " + id));
    }

    public void deleteRestaurant(String id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("Không tồn tại nhà hàng với ID: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}
