package com.foodfast.payment_service.dto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String idProduct;
    private Integer quantity;
    private BigDecimal price;
}
