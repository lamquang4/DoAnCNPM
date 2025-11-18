package main.java.com.foodfast.user_service.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private String id;
    private String name;
    private String speaddress;
    private String ward;
    private String city;
    private LocationDTO location;
    private String ownerId;
    private String fullname; // họ tên chủ nhà hàng
    private Integer status;
    private LocalDateTime createdAt;
}

