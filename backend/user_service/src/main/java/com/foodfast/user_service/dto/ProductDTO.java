package main.java.com.foodfast.user_service.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String image;
    private BigDecimal price;
    private Integer status;
    private String restaurantId;
    private String restaurantName;
    private LocalDateTime createdAt;
}

