package com.foodfast.order_service.model;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String idProduct;
    private Integer quantity;
    private BigDecimal price;
}
