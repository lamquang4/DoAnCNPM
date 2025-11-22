package com.foodfast.restaurant_service.service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import com.foodfast.restaurant_service.client.UserClient;
import com.foodfast.restaurant_service.dto.LocationDTO;
import com.foodfast.restaurant_service.dto.RestaurantDTO;
import com.foodfast.restaurant_service.dto.UserDTO;
import com.foodfast.restaurant_service.model.Restaurant;
import com.foodfast.restaurant_service.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserClient userClient;

    public RestaurantService(RestaurantRepository restaurantRepository, UserClient userClient) {
        this.restaurantRepository = restaurantRepository;
        this.userClient = userClient;
    }

    public Page<RestaurantDTO> getAllRestaurants(String q, Integer status, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Restaurant> data;

        if (status != null) {
            data = restaurantRepository.findByNameContainingIgnoreCaseAndStatus(q != null ? q : "", status, pageable);
        } else {
            data = restaurantRepository.findByNameContainingIgnoreCase(q != null ? q : "", pageable);
        }

        return data.map(this::toDTO);
    }

    public Page<RestaurantDTO> getRestaurantsByUserId(String userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Restaurant> data = restaurantRepository.findByOwnerId(userId, pageable);
        return data.map(this::toDTO);
    }

    public List<RestaurantDTO> getRestaurantsByUserIdSimple(String userId) {
        return restaurantRepository.findByOwnerId(userId).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<RestaurantDTO> getRestaurantsByOwnerIdSimple(String ownerId) {
        return restaurantRepository.findByOwnerId(ownerId).stream()
                .map(this::toDTO)
                .toList();
    }

    public RestaurantDTO updateStatus(String id, int status) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhà hàng với ID: " + id));

        restaurant.setStatus(status);
        restaurantRepository.save(restaurant);

        return this.toDTO(restaurant);
    }

    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    public List<RestaurantDTO> getActiveRestaurants() {
        return restaurantRepository.findByStatus(1).stream()
                .map(this::toDTO)
                .toList();
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
            throw new IllegalArgumentException("Nhà hàng tại vị trí này đã tồn tại");
        }

        restaurant.setStatus(restaurant.getStatus() != 0 ? 1 : 0);
        restaurant.setCreatedAt(Instant.now());
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(String id, Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id)
                .map(existing -> {
                    if (restaurantRepository.existsByNameAndIdNot(updatedRestaurant.getName(), id)) {
                        throw new IllegalArgumentException("Tên nhà hàng đã tồn tại");
                    }

                    if (restaurantRepository.existsByLocation_LatitudeAndLocation_LongitudeAndIdNot(
                            updatedRestaurant.getLocation().getLatitude(),
                            updatedRestaurant.getLocation().getLongitude(),
                            id
                        )) {
                        throw new IllegalArgumentException("Nhà hàng tại vị trí này đã tồn tại");
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
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhà hàng với ID: " + id));
    }

    public void deleteRestaurant(String id) {
        if (!restaurantRepository.existsById(id)) {
            throw new NoSuchElementException("Không tồn tại nhà hàng với ID: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    public RestaurantDTO toDTO(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();

        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setSpeaddress(restaurant.getSpeaddress());
        dto.setWard(restaurant.getWard());
        dto.setCity(restaurant.getCity());
        dto.setLocation(new LocationDTO(
                restaurant.getLocation().getLatitude(),
                restaurant.getLocation().getLongitude()
        ));
        dto.setOwnerId(restaurant.getOwnerId());
        dto.setStatus(restaurant.getStatus());
        dto.setCreatedAt(
                restaurant.getCreatedAt() != null
                        ? restaurant.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        : null
        );

        try {
            UserDTO user = userClient.getUserById(restaurant.getOwnerId());
            dto.setFullname(user != null ? user.getFullname() : null);
        } catch (Exception e) {
            dto.setFullname(null);
        }

        return dto;
    }
}
