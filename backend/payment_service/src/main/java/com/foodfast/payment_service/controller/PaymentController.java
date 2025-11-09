package com.foodfast.payment_service.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.foodfast.payment_service.model.Payment;
import com.foodfast.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
        private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

   @GetMapping("/{orderId}")
    public Payment getPaymentByOrderId(@PathVariable String orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }
}
