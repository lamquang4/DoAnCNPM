package com.foodfast.delivery_service.dto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private String orderCOde;
    private String userId;
    private String fullname;
    private String phone;
    private String address;
    private BigDecimal total;
    private List<OrderItemDTO> items;
    private Integer paymethod; // 0: cod, 1: momo
    private Integer status;
    private String createdAt; 
}
