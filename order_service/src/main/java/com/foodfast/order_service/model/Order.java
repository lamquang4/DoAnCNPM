package com.foodfast.order_service.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class Order {
    @Id
    private String id;

    private String userId;
    private BigDecimal total;

    private List<Item> items;

    private Integer status; // 0: processing, 1: delivery, 2: done
    private LocalDateTime timestamp;

    public void setCreatedAt(LocalDateTime now) {
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Item {
    private String idProduct;
    private Integer quantity;
    private BigDecimal price;
}