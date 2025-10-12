package com.foodfast.payment_service.model.Momo;
import lombok.Data;

@Data
public class CreateMomoResponse {
    private int resultCode; // 0: success
    private String message;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
    private String requestId;
    private String orderId;
    private String partnerCode;
    private long amount;
}
