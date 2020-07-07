
package com.midtrans.api.payload.global;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseQr {

    @JsonProperty("is_qris_flag")
    private Boolean isQrisFlag;
    @JsonProperty("response_code")
    private String responseCode;
    @JsonProperty("hs_qr_string_len")
    private Integer hsQrStringLen;
    @JsonProperty("merchant_address2")
    private String merchantAddress2;
    @JsonProperty("hs_qr_string_enc")
    private String hsQrStringEnc;
    @JsonProperty("merchant_address1")
    private String merchantAddress1;
    @JsonProperty("batch_group_id")
    private Integer batchGroupId;
    @JsonProperty("midware_timestamp")
    private String midwareTimestamp;
    @JsonProperty("merchant_name")
    private String merchantName;
    @JsonProperty("merchant_id")
    private Integer merchantId;
    private String message;
    @JsonProperty("qr_pay_app_id")
    private Integer qrPayAppId;
    private ResponseQrversion version;
    @JsonProperty("batch_group_name")
    private String batchGroupName;
    @JsonProperty("qr_pay_app_name")
    private String qrPayAppName;
    @JsonProperty("print_receipt_merchant_name")
    private String printReceiptMerchantName;
    @JsonProperty("invoice_num")
    private String invoiceNum;
    @JsonProperty("base_amount")
    private Integer baseAmount;
    @JsonProperty("general_ksn_index")
    private String generalKsnIndex;
    @JsonProperty("print_receipt_address_line_2")
    private String printReceiptAddressLine2;
    @JsonProperty("create_at")
    private String createAt;
    @JsonProperty("print_receipt_address_line_1")
    private String printReceiptAddressLine1;
    private String status;

}
