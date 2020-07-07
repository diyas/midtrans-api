package com.midtrans.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.midtrans.api.payload.gopay.ResponseGopay;
import lombok.Data;

import javax.persistence.*;

@Data
//@Entity
//@Table
public class QrTransactionSuccess {

    public QrTransactionSuccess(ResponseGopay responseCharge){
        this.setOrderId(responseCharge.getOrderId());
        this.setCurrency(responseCharge.getCurrency());
        this.setFraudStatus(responseCharge.getFraudStatus());
        this.setGrossAmount(responseCharge.getGrossAmount());
        this.setMerchantId(responseCharge.getMerchantId());
        this.setPaymentType(responseCharge.getPaymentType());
        this.setTransactionId(responseCharge.getTransactionId());
        this.setTransactionStatus(responseCharge.getTransactionStatus());
        this.setTransactionTime(responseCharge.getTransactionTime());
        this.setStatusCode(responseCharge.getStatusCode());
        this.setStatusMessage(responseCharge.getStatusMessage());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String transactionTime;
    @Column
    private String grossAmount;
    @Column
    private String currency;
    @Column
    private String orderId;
    @Column
    private String paymentType;
    @Column
    private String signatureKey;
    @Column
    private String statusCode;
    @Column
    private String transactionId;
    @Column
    private String transactionStatus;
    @Column
    private String fraudStatus;
    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String settlementTime;
    @Column
    private String statusMessage;
    @Column
    private String merchantId;
}
