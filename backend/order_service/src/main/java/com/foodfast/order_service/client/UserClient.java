package com.foodfast.order_service.client;

import com.foodfast.order_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable("id") String id);
}
