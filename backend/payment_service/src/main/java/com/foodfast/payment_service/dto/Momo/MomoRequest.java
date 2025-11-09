package com.foodfast.payment_service.dto.Momo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MomoRequest {
    private String partnerCode;
    private String accessKey;
    private String requestId;
    private String amount;
    private String orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String extraData;
    private String requestType; // "captureWallet" cho QR
    private String signature;
    private String lang;
}
