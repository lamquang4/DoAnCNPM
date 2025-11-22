package com.foodfast.restaurant_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodfast.restaurant_service.dto.RestaurantDTO;
import com.foodfast.restaurant_service.model.Restaurant;
import com.foodfast.restaurant_service.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

     @GetMapping
    public ResponseEntity<?> getAllRestaurants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer status
    ) {
        Page<RestaurantDTO> restaurants = restaurantService.getAllRestaurants(q, status, page, limit);

        return ResponseEntity.ok(Map.of(
                "restaurants", restaurants.getContent(),
                "totalPages", restaurants.getTotalPages(),
                "total", restaurants.getTotalElements()
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRestaurantsByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit
    ) {
        Page<RestaurantDTO> restaurants = restaurantService.getRestaurantsByUserId(userId, page, limit);

        return ResponseEntity.ok(Map.of(
                "restaurants", restaurants.getContent(),
                "totalPages", restaurants.getTotalPages(),
                "total", restaurants.getTotalElements()
        ));
    }

        @GetMapping("/owner/{ownerId}")
    public List<RestaurantDTO> getRestaurantsByOwnerId(@PathVariable String ownerId) {
        return restaurantService.getRestaurantsByOwnerIdSimple(ownerId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RestaurantDTO> updateStatus(
            @PathVariable String id,
            @RequestParam int status
    ) {
        return ResponseEntity.ok(restaurantService.updateStatus(id, status));
    }

    @GetMapping("/user/{userId}/list")
public List<RestaurantDTO> getRestaurantsByUserIdSimple(@PathVariable String userId) {
    return restaurantService.getRestaurantsByUserIdSimple(userId);
}

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id) {
        return restaurantService.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<RestaurantDTO>> getActiveRestaurants() {
        return ResponseEntity.ok(restaurantService.getActiveRestaurants());
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            return ResponseEntity.ok(restaurantService.createRestaurant(restaurant));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id, @RequestBody Restaurant restaurant) {
        try {
            return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable String id) {
  
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.noContent().build();
       
    }
}
