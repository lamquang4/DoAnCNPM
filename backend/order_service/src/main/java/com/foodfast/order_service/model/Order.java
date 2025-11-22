package com.foodfast.order_service.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class Order {

    @Id
    private String id;
    private String orderCode;
    private String userId;
    private String fullname;
    private String phone;
    private String speaddress; 
    private String ward;
    private String city; 
    private BigDecimal total;
     private Location location;
    private List<OrderItem> items;
    private String paymethod;
    private Integer status; // 0: Đang xử lý, 1: Đang giao, 2: Giao thành công, 3: Đã hủy
    @CreatedDate
    @Field("createdAt")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updatedAt")
    private Instant updatedAt;
}
