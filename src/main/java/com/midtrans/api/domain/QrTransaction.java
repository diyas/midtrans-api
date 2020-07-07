package com.midtrans.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.midtrans.api.payload.gopay.ResponseCharge;
import com.midtrans.api.payload.gopay.ResponseGopay;
import lombok.Data;

import javax.persistence.*;

@Data
//@Entity
//@Table
public class QrTransaction {

    public QrTransaction(ResponseGopay responseGopay){
        this.setOrderId(responseGopay.getOrderId());
        this.setCurrency(responseGopay.getCurrency());
        this.setFraudStatus(responseGopay.getFraudStatus());
        this.setGrossAmount(responseGopay.getGrossAmount());
        this.setMerchantId(responseGopay.getMerchantId());
        this.setPaymentType(responseGopay.getPaymentType());
        this.setTransactionId(responseGopay.getTransactionId());
        this.setTransactionStatus(responseGopay.getTransactionStatus());
        this.setTransactionTime(responseGopay.getTransactionTime());
    }

    public QrTransaction(){

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
