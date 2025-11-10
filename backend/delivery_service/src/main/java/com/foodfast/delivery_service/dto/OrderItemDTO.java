package com.foodfast.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String idProduct;
    private Integer quantity;
    private BigDecimal price;
}
