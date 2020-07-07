
package com.midtrans.api.payload.gopay;

import lombok.Data;

@Data
public class ItemDetail {

    private String id;
    private String name;
    private long price;
    private long quantity;

}
