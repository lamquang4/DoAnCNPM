package com.foodfast.payment_service.client;

import com.foodfast.payment_service.model.Momo.CreateMomoRequest;
import com.foodfast.payment_service.model.Momo.CreateMomoResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "momo", url = "${momo.url}")
public interface MomoApi {

 @PostMapping("/create")
    CreateMomoResponse createPayment(@RequestBody CreateMomoRequest createMomoRequest);
}
