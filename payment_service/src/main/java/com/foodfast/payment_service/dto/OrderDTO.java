package com.foodfast.payment_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
        private String id;

    private String userId;
    private String fullname;
    private String phone;
    private String address;
    private BigDecimal total;
    private List<OrderItemDTO> items;
    private Integer paymethod; // 0: cod, 1: momo
    private Integer status; // 0: processing, 1: delivery, 2: done
    private String createdAt;
    private String updatedAt;
}
