package com.foodfast.payment_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodfast.payment_service.dto.OrderDTO;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/api/order/{id}")
    OrderDTO getOrderById(@PathVariable("id") String id);

    @GetMapping("/api/order/code/{orderCode}")
    OrderDTO getOrderByCode(@PathVariable("orderCode") String orderCode);

    @PutMapping("/api/order/code/{orderCode}")
    void updateOrderStatus(
            @PathVariable("orderCode") String orderCode,
            @RequestParam("status") Integer status
    );
}
