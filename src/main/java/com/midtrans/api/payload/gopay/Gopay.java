
package com.midtrans.api.payload.gopay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Gopay {

    @SerializedName("callback_url")
    private String callbackUrl;
    @SerializedName("enable_calback")
    private Boolean enableCallback;

}
