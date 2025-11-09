package com.foodfast.delivery_service.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "delivery")
public class Delivery {
    @Id
    private String id; 
    private String orderId; 
    private String droneId;
    private Location origin;       // nhà hàng
    private Location destination;  // khách hàng
    private Location currentLocation; // vị trí hiện tại drone
    private int status; // 0: Pending, 1: Delivering, 2: Delivered , -1: Cancelled
}
