package com.foodfast.order_service.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "drone")
public class Drone {
    @Id
    private String id;
    private String restaurantId;
    private String model;
    private double capacity; // khối lượng kg tối đa drone có thể vận chuyển
    private double battery; // số pin còn lại
    private double range; // quãng đường bay tối đa km
    private Integer status; // 0: Đang rảnh, 1: Đang giao, 2: Giao thành công
        @CreatedDate
    @Field("createdAt")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updatedAt")
    private Instant updatedAt;
}

