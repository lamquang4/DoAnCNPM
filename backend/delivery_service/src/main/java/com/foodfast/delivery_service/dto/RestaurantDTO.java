package com.foodfast.delivery_service.dto;

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
    private int status;
}
