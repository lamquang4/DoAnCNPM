package com.foodfast.payment_service.service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.foodfast.payment_service.client.OrderClient;
import com.foodfast.payment_service.dto.OrderDTO;
import com.foodfast.payment_service.dto.PaymentDTO;
import com.foodfast.payment_service.model.Payment;
import com.foodfast.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient; 

    public PaymentService(PaymentRepository paymentRepository, OrderClient orderClient) {
        this.paymentRepository = paymentRepository;
        this.orderClient = orderClient;
    }

    public Page<PaymentDTO> getAllPayments(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        Page<Payment> payments = paymentRepository.findAll(pageable);

        List<PaymentDTO> dtoList = payments.stream()
                .map(this::convertToDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, payments.getTotalElements());
    }

    private PaymentDTO convertToDTO(Payment payment) {
        String orderCode = null;
        try {
            OrderDTO order = orderClient.getOrderByCode(payment.getOrderId());
            if (order != null) {
                orderCode = order.getOrderCode();
            }
        } catch (Exception ignored) {}

        return new PaymentDTO(
                payment.getId(),
                payment.getOrderId(),
                payment.getTransactionId(),
                orderCode,  // điền orderCode từ order-service
                payment.getAmount(),
                payment.getPaymethod(),
                payment.getStatus(),
                payment.getCreatedAt() != null
                        ? LocalDateTime.ofInstant(payment.getCreatedAt(), ZoneId.systemDefault())
                        : null
        );
    }
}

