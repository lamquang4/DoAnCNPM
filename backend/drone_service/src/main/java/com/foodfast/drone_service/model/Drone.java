package com.foodfast.drone_service.model;
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
    private double range; // Khoảng cách km tối đa drone có thể bay
    private Integer status; // 0 là đang rảnh, 1 đang giao, 2 giao thành công, 3 đang bay về, 4 bảo trì
        @CreatedDate
    @Field("createdAt")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updatedAt")
    private Instant updatedAt;
}
