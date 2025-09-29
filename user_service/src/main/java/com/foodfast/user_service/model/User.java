package com.foodfast.user_service.model;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String fullname;
    private String phone;
    private String speaddress;
    private String city;
    private String ward;
    @JsonIgnore
    private String password;
}
