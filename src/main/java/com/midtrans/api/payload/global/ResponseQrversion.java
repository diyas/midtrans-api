
package com.midtrans.api.payload.global;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseQrversion {

    @JsonProperty("device_crypto_ver")
    private String deviceCryptoVer;

}
