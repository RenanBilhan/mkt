package com.example.mkt.dto.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StripePaymentOutputDTO {
   private Integer idPedido;
   private String clientSecret;
   private String idIntent;
   private String paymentStatus;
   private String orderStatus;

}
