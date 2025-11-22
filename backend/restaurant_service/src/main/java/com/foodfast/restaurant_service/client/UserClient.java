package com.foodfast.restaurant_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.foodfast.restaurant_service.dto.UserDTO;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable("id") String id);
}

