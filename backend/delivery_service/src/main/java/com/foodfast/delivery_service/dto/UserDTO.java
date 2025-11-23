package com.foodfast.delivery_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String fullname;
    private String email;
    private String phone;
    private Integer role;
    private Integer status;
    private LocalDateTime createdAt;
}
