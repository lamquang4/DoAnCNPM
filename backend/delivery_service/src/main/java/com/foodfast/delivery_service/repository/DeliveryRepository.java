package com.foodfast.delivery_service.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.foodfast.delivery_service.model.Delivery;
@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    List<Delivery> findAllByOrderId(String orderId);
}
