
package com.midtrans.api.payload.gopay;

import lombok.Data;

@Data
public class QrCodeData {

    private String tref;
    private Integer amount;

}
