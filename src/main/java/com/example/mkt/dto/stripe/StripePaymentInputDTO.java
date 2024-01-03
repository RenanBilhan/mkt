package com.example.mkt.dto.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StripePaymentInputDTO {

//    @SerializedName("items")
//    Object[] items;

    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvc;
}
