package com.foodfast.payment_service.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.foodfast.payment_service.dto.Momo.MomoResponse;
import com.foodfast.payment_service.service.MomoService;

@RestController
@RequestMapping("/api/payment/momo")
public class MomoController {

    private MomoService momoService;

    public MomoController(MomoService momoService) {
        this.momoService = momoService;
    }

    // tạo mã qr Momo để thanh toán
    @PostMapping("/qr/{orderCode}")
    public ResponseEntity<MomoResponse> payWithMomo(@PathVariable String orderCode) throws Exception {
        MomoResponse response = momoService.createPayment(orderCode);
        return ResponseEntity.ok(response);
    }

}
