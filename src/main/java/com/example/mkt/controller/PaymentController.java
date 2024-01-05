package com.example.mkt.controller;

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
public class PaymentController {
    
    private final StripeService chargeService;

    @PostMapping("/create/payment/intent/{idPedido}")
    public StripePaymentOutputDTO createPayment(@PathVariable Integer idPedido) throws StripeException, BussinessRuleException {
        return chargeService.createPaymentIntent(idPedido);
    }

    @PostMapping("/confirm/payment/{idPedido}")
    @ResponseBody
    public ResponseEntity<StripePaymentOutputDTO> confirmPayment(@PathVariable Integer idPedido, @RequestBody String paymentMethodId) throws StripeException {
        return new ResponseEntity<>(chargeService.confirmPayment(idPedido,  paymentMethodId), HttpStatus.OK);
    }

//    @PostMapping("/card/token")
//    @ResponseBody
//    public ResponseEntity<StripeTokenDTO> createCardToken(@RequestBody StripeTokenDTO model) throws StripeException {
//        return new ResponseEntity<>(stripeTokenService.createCardToken(model), HttpStatus.OK);
//    }

//    @PostMapping("/charge")
//    @ResponseBody
//    public ResponseEntity<StripeChargeOutputDTO> charge(@RequestBody StripeChargeInputDTO model, Integer pedidoId) throws RegraDeNegocioException {
//        return new ResponseEntity<>(chargeService.charge(model, pedidoId), HttpStatus.OK);
//    }


}
