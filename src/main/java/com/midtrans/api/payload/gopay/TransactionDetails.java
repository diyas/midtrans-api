
package com.midtrans.api.payload.gopay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TransactionDetails {

    @JsonProperty("gross_amount")
    private Double grossAmount;
    @JsonProperty("order_id")
    private String orderId;

}
