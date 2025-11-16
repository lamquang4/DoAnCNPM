package com.foodfast.restaurant_service.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.foodfast.restaurant_service.model.Restaurant;
@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    
}
