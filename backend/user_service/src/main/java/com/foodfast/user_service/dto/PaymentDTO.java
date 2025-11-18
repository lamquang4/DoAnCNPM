package main.java.com.foodfast.user_service.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String id;
    private String orderId;
    private String transactionId;
    private String orderCode;
    private BigDecimal amount;
    private String paymethod;
    private Integer status;
    private LocalDateTime createdAt;
}
