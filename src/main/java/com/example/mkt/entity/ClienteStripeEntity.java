package com.example.mkt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteStripeEntity {

    private String nomeStripe;
    private String emailStripe;
    private String idStripe;
}
