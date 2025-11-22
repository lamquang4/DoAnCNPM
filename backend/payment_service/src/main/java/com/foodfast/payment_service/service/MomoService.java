package com.foodfast.payment_service.service;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.foodfast.payment_service.client.CartClient;
import com.foodfast.payment_service.client.OrderClient;
import com.foodfast.payment_service.dto.OrderDTO;
import com.foodfast.payment_service.dto.Momo.MomoRequest;
import com.foodfast.payment_service.dto.Momo.MomoResponse;
import com.foodfast.payment_service.model.Payment;
import com.foodfast.payment_service.repository.PaymentRepository;

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

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final CartClient cartClient;
    private final RestTemplate restTemplate = new RestTemplate();

    public MomoService(PaymentRepository paymentRepository, OrderClient orderClient, CartClient cartClient) {
        this.paymentRepository = paymentRepository;
        this.orderClient = orderClient;
        this.cartClient = cartClient;
    }

 public MomoResponse createPayment(String orderCode) throws Exception {
        String requestId = UUID.randomUUID().toString();
        String orderId = orderCode;
        String amount = "100000";
        String orderInfo = "Thanh toan don hang " + orderId;
        String extraData = "";

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

@Transactional
 public boolean handleSuccessfulPayment(String orderCode, String transId) {

    if (paymentRepository.existsByTransactionId(transId)) {
        return true;
    }

    OrderDTO order = orderClient.getOrderByCode(orderCode);
    if (order == null) {
        return false;
    }

    BigDecimal amount = order.getTotal();

    Payment payment = Payment.builder()
            .orderId(order.getId())
            .transactionId(transId)
            .status(1)
            .amount(amount)
            .paymethod("MOMO")
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    paymentRepository.save(payment);
    cartClient.clearCart(order.getUserId());
    orderClient.updateOrderStatus(order.getId(), 0);

    return true;
}


}
