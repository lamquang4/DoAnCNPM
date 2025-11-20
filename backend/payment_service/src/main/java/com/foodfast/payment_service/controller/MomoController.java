package com.foodfast.payment_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.foodfast.payment_service.dto.Momo.MomoResponse;
import com.foodfast.payment_service.service.MomoService;

@RestController
@RequestMapping("/api/payment/momo")
public class MomoController {

    @Value("${frontend.url}")
    private String frontendUrl;

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

 @GetMapping("/redirect")
public ResponseEntity<Void> handleRedirect(
        @RequestParam(required = false) String resultCode,
        @RequestParam(required = false) String orderId,
        @RequestParam(required = false) String transId,
        @RequestParam(required = false) String message
) throws Exception {

    String redirectUrl;

    if ("0".equals(resultCode)) {

        boolean success = momoService.handleSuccessfulPayment(orderId, transId);

        if (success) {
            redirectUrl = frontendUrl + "/order-result?result=successfully&orderCode=" + orderId;
        } else {
            redirectUrl = frontendUrl + "/order-result?result=fail";
        }

    } else {
        redirectUrl = frontendUrl + "/order-result?result=fail";
    }

    return ResponseEntity.status(302)
            .header("Location", redirectUrl)
            .build();
}


}
