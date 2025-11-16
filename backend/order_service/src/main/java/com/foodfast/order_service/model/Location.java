package com.foodfast.order_service.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private double latitude; // vĩ độ
    private double longitude; // kinh độ
}
