
package com.midtrans.api.payload.gopay;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestCharge {

    @JsonProperty("customer_details")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CustomerDetails customerDetails;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Gopay gopay;
    @JsonProperty("item_details")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ItemDetail> itemDetails;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("transaction_details")
    private TransactionDetails transactionDetails;

}
