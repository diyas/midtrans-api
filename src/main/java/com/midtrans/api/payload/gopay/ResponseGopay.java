package com.midtrans.api.payload.gopay;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseGopay {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("transaction_time")
    private String transactionTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("gross_amount")
    private String grossAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order_id")
    private String orderId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("signature_key")
    private String signatureKey;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("status_code")
    private String statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("transaction_status")
    private String transactionStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("fraud_status")
    private String fraudStatus;
    @JsonProperty("settlement_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String settlementTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("payment_option_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentOptionType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Action> actions;
}
