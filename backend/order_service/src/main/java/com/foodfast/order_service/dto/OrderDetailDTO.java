package com.foodfast.order_service.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private String productId;
    private String name; 
 private String image;
    private Integer quantity; // số lượng mua
    private BigDecimal price; // giá book tại thời điểm đặt đơn
}