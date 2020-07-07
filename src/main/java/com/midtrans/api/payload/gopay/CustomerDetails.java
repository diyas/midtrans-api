
package com.midtrans.api.payload.gopay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CustomerDetails {

    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String phone;

}
