package main.java.com.foodfast.user_service.dto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String productId;
    private String name;
    private String image;
    private BigDecimal price; // giá tại lúc đặt hàng
    private Integer quantity; // số lượng mua
}
