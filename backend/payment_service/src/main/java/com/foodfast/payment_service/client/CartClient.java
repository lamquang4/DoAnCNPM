package com.foodfast.payment_service.client;

import com.foodfast.payment_service.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartClient {

    @DeleteMapping("/api/cart/clear/{userId}")
    CartDTO clearCart(@PathVariable("userId") String userId);
}
