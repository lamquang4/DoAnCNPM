package com.foodfast.payment_service.repository;
import com.foodfast.payment_service.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
     boolean existsByTransactionId(String transactionId);
}
