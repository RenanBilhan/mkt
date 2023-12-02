package com.example.mkt.controller;

import com.example.mkt.entity.ClienteStripeEntity;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
public class PagamentoController {

    @Value("${stripe.apikey}")
    String apiKey;

    @RequestMapping("/create/costumer")
    public ClienteStripeEntity index(@RequestBody ClienteStripeEntity data) throws StripeException {
        Stripe.apiKey = apiKey;
        Map<String, Object> params = new HashMap<>();
        params.put("name", data.getNomeStripe());
        params.put("email", data.getEmailStripe());
        Customer customer = Customer.create(params);
        data.setIdStripe(customer.getId());
        return data;
    }
}
