package com.foodfast.cart_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private String productId;
    private Integer quantity;
        private String image; 
    private String name; 
    private BigDecimal price;
}
