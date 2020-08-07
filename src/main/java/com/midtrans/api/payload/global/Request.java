package com.midtrans.api.payload.global;

import lombok.Data;

@Data
public class Request {
    private String userId;
    private String orderId;
    private String paymentType;
    private Double amount;
}
