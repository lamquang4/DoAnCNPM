package com.foodfast.payment_service.model.Momo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMomoRequest {
    private String partnerCode;
    private String accessKey;
    private String requestId;
    private long amount;
    private String orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String extraData;
    private String requestType;
    private String signature;
}
