package com.example.mkt.controller;

import com.example.mkt.documentation.PaymentControllerDoc;
import com.example.mkt.dto.stripe.*;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.service.StripeService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
public class PaymentController implements PaymentControllerDoc {
    
    private final StripeService chargeService;

    @PostMapping("/create/payment/intent/{idPedido}")
    public ResponseEntity<StripePaymentOutputDTO> createPayment(@PathVariable Integer idPedido) throws StripeException, BussinessRuleException {
        return new ResponseEntity<>(chargeService.createPaymentIntent(idPedido), HttpStatus.CREATED);
    }

    @PostMapping("/confirm/payment/{idPedido}")
    @ResponseBody
    public ResponseEntity<StripePaymentOutputDTO> confirmPayment(@PathVariable Integer idPedido, @RequestBody String paymentMethodId) throws StripeException {
        return new ResponseEntity<>(chargeService.confirmPayment(idPedido,  paymentMethodId), HttpStatus.ACCEPTED);
    }

}
