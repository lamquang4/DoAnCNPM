package com.foodfast.drone_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private String orderCode;
    private String fullname;
    private String phone;
    private String speaddress;
    private String city;
    private String ward;
    private LocationDTO location;
    private String paymethod;
    private Integer status;
    private Double total;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
            private DeliveryDTO delivery;
}
