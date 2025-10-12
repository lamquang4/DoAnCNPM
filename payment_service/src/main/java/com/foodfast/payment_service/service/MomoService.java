package com.foodfast.payment_service.service;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.foodfast.payment_service.client.MomoApi;
import com.foodfast.payment_service.model.Momo.CreateMomoRequest;
import com.foodfast.payment_service.model.Momo.CreateMomoResponse;

@Service
public class MomoService {

@Value("${momo.partner-code}")
private String partnerCode;

@Value("${momo.access-key}")
private String accessKey;

@Value("${momo.secret-key}")
private String secretKey;

@Value("${momo.redirect-url}")
private String redirectUrl;

@Value("${momo.ipn-url}")
private String ipnUrl;


private MomoApi momoApi;

public MomoService (MomoApi momoApi){
    this.momoApi = momoApi;
}

public CreateMomoResponse createMomoQR() {
    try {
        long timestamp = System.currentTimeMillis();
        int random = (int)(Math.random() * 10000);
        String orderId = "ORD" + timestamp + random;
        String requestId = "REQ" + timestamp + random;
        String orderInfo = "Thanh toán đơn hàng " + orderId;
        String requestType = "captureWallet";
        String extraData = "";
        long amount = 100000;

        String rawSignature = String.format(
                "accessKey=%s&amount=%d&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                accessKey, amount, extraData, ipnUrl, orderId, orderInfo, partnerCode, redirectUrl, requestId, requestType
        );

        String signature = signHmacSHA256(rawSignature, secretKey);

        CreateMomoRequest request = CreateMomoRequest.builder()
                .partnerCode(partnerCode)
                .accessKey(accessKey)
                .requestId(requestId)
                .amount(amount)
                .orderId(orderId)
                .orderInfo(orderInfo)
                .redirectUrl(redirectUrl)
                .ipnUrl(ipnUrl)
                .extraData(extraData)
                .requestType(requestType)
                .signature(signature)
                .build();

        return momoApi.createPayment(request);

    } catch (Exception e) {
        throw new RuntimeException("Lỗi thanh toán MoMo: " + e.getMessage(), e);
    }
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
