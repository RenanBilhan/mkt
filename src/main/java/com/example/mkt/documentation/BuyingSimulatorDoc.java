package com.example.mkt.documentation;

import com.example.mkt.dto.stripe.StripePaymentInputDTO;
import com.example.mkt.dto.stripe.StripePaymentOutputDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BuyingSimulatorDoc {

    @Operation(summary = "Create a payment method (credit card).", description = "Recives the credit card information and send straight away to the Stripe API, preserving the LGPD (LGPD means General Data Protection Law regulations) rules (brazilian data protection law) " +
    "It is important to notice that this controller is a simulation of the Frontend  interaction, so the credit card data won't touch this application API (backend) to respect the LGPD rules.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the payment method id sent by Stripe API."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to create the payment method."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to create the method."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    public String createPaymentMethod(StripePaymentInputDTO card) throws BussinessRuleException;

    @Operation(summary = "Simulate a success buy.", description = "Recives id of the order to be payd and the id of the payment method. Simulates a " +
            "buying action with the following flow:" +
            " -> Retrive the payment method using the id in the parameters." +
            " -> Create the Payment intent object in Stripe API." +
            " -> Try to update the stock, reducing the amount buyed (if the sotock is not enough, an exception is trhown)" +
            " -> Use the id order and the payment method  to confirm the payment.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a StripePaymentOutputDTO, with the status successful."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to confirm the payment."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to confirm the payment."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping("/{idOrder}")
    public ResponseEntity<StripePaymentOutputDTO> buyingSimulation(Integer idOrder, @RequestBody String idCard) throws StripeException, BussinessRuleException;
}
