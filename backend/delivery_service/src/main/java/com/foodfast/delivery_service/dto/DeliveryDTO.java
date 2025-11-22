package com.foodfast.delivery_service.dto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private String id;
    private String orderId;
    private String droneId;
    private String restaurantId;
    private String restaurantName; 
    private LocationDTO restaurantLocation; // vị trí nhà hàng của drone
    private LocationDTO currentLocation; // vị trí hiện tại của drone
    private LocalDateTime createdAt;
}
