package com.example.mkt.documentation;

import com.example.mkt.dto.stripe.StripePaymentOutputDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface PaymentControllerDoc {

    @Operation(summary = "Save a new payment intent.", description = "Save a new PaymentIntent directly on the Stripe API and save it's id on the OrderEntity. " +
            "You can sand the payment information by sending the order id. Must create a order first on the OrderController.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a StripePaymentOutputDTO with the information you will need to confirm it."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to save the PaymentIntent."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to save a PaymentIntent."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping("/create/payment/intent/{idPedido}")
    public ResponseEntity<StripePaymentOutputDTO> createPayment(@PathVariable Integer idPedido) throws StripeException, BussinessRuleException;

    @Operation(summary = "Confirm the payment of an existing PaymentIntent.", description = "Provides the PaymentMethod and confirm the payment of and existing PaymentIntent. " +
            "You can send the payment information by sending the order id and retrive the credit card information by sending it's id. Must create a PaymentIntent at the PaymentController and a PaymentMethod at the BuyingSimulator first." +
            "The test token to use on Stripe API credit card is:" +
            "card number: 4242424242424242" +
            "exp month: any future month" +
            "exp year: any future year" +
            "cvc: any 3 numbers combination (123 for exemple)" +
            "user name: any name")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a StripePaymentOutputDTO with status successful."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to confirm the PaymentIntent."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to confirm a PaymentIntent."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping("/confirm/payment/{idPedido}")
    @ResponseBody
    public ResponseEntity<StripePaymentOutputDTO> confirmPayment(@PathVariable Integer idPedido, @RequestBody String paymentMethodId) throws StripeException;
}
