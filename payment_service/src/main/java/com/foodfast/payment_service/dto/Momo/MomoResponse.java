package com.foodfast.payment_service.dto.Momo;
import lombok.Data;

@Data
public class MomoResponse {
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
    private String orderId;
    private int resultCode;
    private String message;
}
