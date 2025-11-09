package com.foodfast.order_service.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodfast.order_service.client.UserClient;
import com.foodfast.order_service.dto.UserDTO;

@RestController
@RequestMapping("/api/user")
public class UserController {
        private final UserClient userClient;

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/{id}")
    public UserDTO getPUserById(@PathVariable String id) {
        return userClient.getUserById(id);
    }
}
