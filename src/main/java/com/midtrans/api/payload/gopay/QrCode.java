
package com.midtrans.api.payload.gopay;

import lombok.Data;

@Data
public class QrCode {

    private String activity;
    private QrCodeData data;

}
