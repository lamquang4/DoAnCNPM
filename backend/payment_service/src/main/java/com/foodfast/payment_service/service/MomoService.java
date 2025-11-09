package com.foodfast.payment_service.service;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.foodfast.payment_service.dto.Momo.MomoRequest;
import com.foodfast.payment_service.dto.Momo.MomoResponse;

@Service
public class MomoService {

@Value("${momo.partner-code}")
private String partnerCode;

@Value("${momo.access-key}")
private String accessKey;

@Value("${momo.secret-key}")
private String secretKey;

@Value("${momo.url}")
private String momoUrl;

@Value("${momo.redirect-url}")
private String redirectUrl;

@Value("${momo.ipn-url}")
private String ipnUrl;

    @Value("${momo.refund-url}")
    private String refundUrl;

   private final RestTemplate restTemplate = new RestTemplate();

 public MomoResponse createPayment(String orderCode) throws Exception {
        String requestId = UUID.randomUUID().toString();
        String orderId = orderCode;
        String amount = "100000";
        String orderInfo = "Thanh toan don hang " + orderId;
        String extraData = "";

        // Chuỗi để ký
        String rawSignature =
                "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=captureWallet";

        // Tạo chữ ký HMAC SHA256
        String signature = signHmacSHA256(rawSignature, secretKey);

        // Tạo request
        MomoRequest request = MomoRequest.builder()
                .partnerCode(partnerCode)
                .accessKey(accessKey)
                .requestId(requestId)
                .amount(amount)
                .orderId(orderId)
                .orderInfo(orderInfo)
                .redirectUrl(redirectUrl)
                .ipnUrl(ipnUrl)
                .extraData(extraData)
                .requestType("captureWallet")
                .lang("vi")
                .signature(signature)
                .build();

        MomoResponse response = restTemplate.postForObject(
                momoUrl,
                request,
                MomoResponse.class
        );

        return response;
    }

private String signHmacSHA256(String data, String key) throws Exception {
    Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    hmacSHA256.init(secretKey);
    byte[] hash = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));

    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
}
}
