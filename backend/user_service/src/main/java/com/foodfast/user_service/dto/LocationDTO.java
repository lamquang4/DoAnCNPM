package main.java.com.foodfast.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private double latitude;   // vĩ độ
    private double longitude;  // kinh độ
}