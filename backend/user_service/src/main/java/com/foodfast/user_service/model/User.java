package com.foodfast.user_service.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String fullname;
    private String email;
    private String phone;
    private String password;
    private Integer status;
}
