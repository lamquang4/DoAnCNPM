package com.foodfast.payment_service.model;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "payment")
public class Payment {
    @Id
    private String id;

   private String orderId;
    private String userId;

    private String requestId;     // Mã request gửi đến cổng thanh toán
    private String transId;       // Mã giao dịch do cổng trả về
    private BigDecimal amount;
    private String currency;      // "VND", "USD", ...
    private String status; 

    private String provider;      // "MOMO", "PAYPAL", "VNPAY", "COD"
    private String method;        // "WALLET", "CARD", "BANK", "CASH"

    private Integer resultCode;   // Mã kết quả từ cổng thanh toán
    private String message;       // Thông báo hoặc mô tả lỗi
    private String signature;     // Chữ ký để kiểm tra
    private Boolean verified;     // Đã xác minh chữ ký hợp lệ chưa

    private Map<String, Object> rawResponse; 

    @CreatedDate
    @Field("createdAt")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updatedAt")
    private Instant updatedAt;
}
