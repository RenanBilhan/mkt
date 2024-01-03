package com.example.mkt.service;

import com.example.mkt.dto.stripe.*;
import com.example.mkt.entity.ClientEntity;
import com.example.mkt.entity.OrderEntity;
import com.example.mkt.entity.enums.OrderStatus;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripeService {

    private final OrderRepository pedidoRepository;

    @Value("${stripe.api.key}")
    String apiKey;

    public StripePaymentOutputDTO createPaymentIntent(Integer idPedido) throws StripeException, BussinessRuleException {

        Stripe.apiKey = apiKey;
        OrderEntity pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new EntitiesNotFoundException("Pedido não encontrado"));

        if(pedido.getStatus().toString().toUpperCase() != "AGUARDANDO_PAGAMENTO"){
            throw new BussinessRuleException("O pagamento ja foi efetuado.");
        }

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("brl")
                .setAmount((long)(pedido.getPrecoTotalProdutos() * 100L))
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
        pedido.setIdPagamentoStripe(intent.getId());
        pedidoRepository.save(pedido);

        return new StripePaymentOutputDTO(idPedido, intent.getClientSecret(), intent.getId(), intent.getStatus(), OrderStatus.AGUARDANDO_PAGAMENTO.toString());

    }

    public StripePaymentOutputDTO confirmPayment(Integer idOrder, String paymentMethodId) throws StripeException {

        OrderEntity order = pedidoRepository.findById(idOrder).orElseThrow(() -> new EntitiesNotFoundException("Order not found."));

        ClientEntity cliente = order.getCliente();


        Stripe.apiKey = apiKey;
        PaymentIntent resource = PaymentIntent.retrieve(order.getIdPagamentoStripe());

        PaymentIntentConfirmParams confirmParams =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod(paymentMethodId)
                        .setReturnUrl("http://localhost:8080/swagger-ui/index.html#/pagamento-controller/confirmPayment_1")
                        .setReceiptEmail(cliente.getEmailCliente())
                        .build();
        PaymentIntent paymentIntent = resource.confirm(confirmParams);
        order.setStatus(OrderStatus.PAGO);
        pedidoRepository.save(order);
        StripePaymentOutputDTO outputDTO = new StripePaymentOutputDTO(order.getIdPedido(), paymentIntent.getClientSecret(),
                paymentIntent.getId(),paymentIntent.getStatus(), order.getStatus().toString());

        return outputDTO;
    }

}
