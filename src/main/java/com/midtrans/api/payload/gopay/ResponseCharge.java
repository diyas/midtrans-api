package com.midtrans.api.payload.gopay;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseCharge {

    //private List<Action> actions;
    private String currency;
    @JsonProperty("fraud_status")
    private String fraudStatus;
    @JsonProperty("gross_amount")
    private String grossAmount;
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("transaction_status")
    private String transactionStatus;
    @JsonProperty("transaction_time")
    private String transactionTime;
//    @JsonProperty("channel_response_code")
//    private String channelResponseCode;
//    @JsonProperty("channel_response_message")
//    private String channelResponseMessage;
}
