package com.foodfast.restaurant_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodfast.restaurant_service.model.Restaurant;
import com.foodfast.restaurant_service.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Lấy tất cả nhà hàng
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // Lấy nhà hàng theo ID
    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    // Tạo mới nhà hàng
    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setStatus(1); // mặc định đang hoạt động
        return restaurantRepository.save(restaurant);
    }

    // Cập nhật thông tin nhà hàng
    public Restaurant updateRestaurant(String id, Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id)
                .map(existing -> {
                 existing.setName(updatedRestaurant.getName());
                    existing.setSpeaddress(updatedRestaurant.getSpeaddress());
                    existing.setWard(updatedRestaurant.getWard());
                    existing.setCity(updatedRestaurant.getCity());
                    existing.setLocation(updatedRestaurant.getLocation());
                    existing.setStatus(updatedRestaurant.getStatus());
                    return restaurantRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng với ID: " + id));
    }

    // Xóa nhà hàng
    public void deleteRestaurant(String id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("Không tồn tại nhà hàng với ID: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}
