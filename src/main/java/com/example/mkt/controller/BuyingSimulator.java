package com.example.mkt.controller;

import com.example.mkt.dto.stock.StockOutputDTO;
import com.example.mkt.dto.stripe.StripePaymentInputDTO;
import com.example.mkt.dto.stripe.StripePaymentOutputDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/simulator")
public class BuyingSimulator {

    private final PaymentController pagamentoController;
    private final StockController estoqueController;

    @Value("${stripe.public.key}")
    String publicKey;

    @PostMapping("/{idOrder}")
    public ResponseEntity<StripePaymentOutputDTO> buyingSimulation(Integer idOrder, @RequestBody StripePaymentInputDTO card) throws StripeException, BussinessRuleException {

        PaymentMethod paymentMethod = createPaymentMethod(card);

        StripePaymentOutputDTO paymentIntent = pagamentoController.createPayment(idOrder);
        List<StockOutputDTO> estoqueUpdate = estoqueController.reduceStock(idOrder).getBody();
        return pagamentoController.confirmPayment(idOrder, paymentMethod.getId());
    }

    public PaymentMethod createPaymentMethod(StripePaymentInputDTO card) throws BussinessRuleException {
        Stripe.apiKey=publicKey;

        try{

//            StripeTokenDTO input = new StripeTokenDTO();
//            input.setCvc(card.getCvc());
//            input.setExpYear(stripePaymentInputDTO.getExpYear());
//            input.setCardNumber(stripePaymentInputDTO.getCardNumber());
//            input.setExpMonth(stripePaymentInputDTO.getExpMonth());
//            input.setUserName(stripePaymentInputDTO.getUserName());
//
//            StripeTokenDTO stripeTokenDTO = stripeTokenService.createCardToken(input);

            PaymentMethodCreateParams params =
                    PaymentMethodCreateParams.builder()
                            .setType(PaymentMethodCreateParams.Type.CARD)
                            .setCard(
                                    PaymentMethodCreateParams.CardDetails.builder()
                                            .setNumber(card.getCardNumber())
                                            .setExpMonth(Long.parseLong(card.getExpMonth()))
                                            .setExpYear(Long.parseLong(card.getExpYear()))
                                            .setCvc(card.getCvc())
                                            .build()
                            )
                            .build();
            PaymentMethod paymentMethod = PaymentMethod.create(params);
            return paymentMethod;
        } catch (StripeException e) {
            throw new BussinessRuleException("Invalid card");
        }
    }
}
