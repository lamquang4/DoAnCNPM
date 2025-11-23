package com.foodfast.payment_service.controller;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodfast.payment_service.dto.PaymentDTO;
import com.foodfast.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
        private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPayments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit
    ) {
        Page<PaymentDTO> payments = paymentService.getAllPayments(page, limit);

        return ResponseEntity.ok(Map.of(
                "payments", payments.getContent(),
                "totalPages", payments.getTotalPages(),
                "total", payments.getTotalElements()
        ));
    }

}
