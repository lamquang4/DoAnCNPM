package com.foodfast.payment_service.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.foodfast.payment_service.model.Payment;
import com.foodfast.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

public PaymentService (PaymentRepository paymentRepository){
    this.paymentRepository  = paymentRepository;
}

 public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findAll()
                .stream()
                .filter(p -> orderId.equals(p.getOrderId()))
                .findFirst()
                .orElse(null);
    }
 
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

}
