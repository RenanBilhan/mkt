package com.example.mkt.service;

import com.example.mkt.dto.order.ItemOutputDTO;
import com.example.mkt.dto.order.OrderInputDTO;
import com.example.mkt.dto.order.OrderOutputDTO;
import com.example.mkt.dto.orderStock.OrderStockInputDTO;
import com.example.mkt.entity.*;
import com.example.mkt.entity.enums.OrderStatus;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.*;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final StockRepository stockRepository;

//    private final EmailService emailService;

    private final OrderStockRepository orderStockRepository;

    public List<OrderOutputDTO> findAll(){
        List<ItemOutputDTO> items = new ArrayList<>();
        return orderRepository.findAll().stream()
                .map(OrderOutputDTO::new)
                .collect(Collectors.toList());
    }

    public OrderOutputDTO save(Integer idClient, OrderInputDTO orderInputDTO) throws BussinessRuleException, MessagingException {

        List<OrderStockInputDTO> items = orderInputDTO.getItems();

        ClientEntity client = clientRepository.findById(idClient)
                .orElseThrow(() -> new EntitiesNotFoundException("Client not found."));

        OrderEntity newOrder = new OrderEntity();

        newOrder.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        newOrder.setFreight(0.0);
        newOrder.setPriceTotalProducts(0.0);
        newOrder.setClient(client);

        OrderEntity createdOrder = orderRepository.save(newOrder);

        List<OrderStockEntity> orderStockList = new ArrayList<>();

        for(OrderStockInputDTO item : items) {


            Double currentValue = newOrder.getPriceTotalProducts();

            StockEntity stock = stockRepository.findById(item.getIdStock())
                            .orElseThrow(() -> new EntitiesNotFoundException("Stock not found."));

            OrderStockEntity orderStockEntity = new OrderStockEntity(
                    newOrder,
                    stock,
                    item.getAmountOfBuying(),
                    stock.getProduct().getPrice()*item.getAmountOfBuying(),
                    stock.getSize()
            );

            orderStockList.add(orderStockEntity);
            newOrder.setItems(orderStockList);
            newOrder.setPriceTotalProducts(currentValue += orderStockEntity.getPrice());

        }

        newOrder.setItems(orderStockList);

        OrderEntity savedOrder = orderRepository.save(newOrder);

        return ConversorMapper.converterPedidoOutputDTO(savedOrder);
    }

    public OrderOutputDTO confirmPayment(Integer idOrder) throws MessagingException {
        OrderEntity orderEntity = orderRepository.findById(idOrder).orElseThrow(() -> new EntitiesNotFoundException("Order not found."));

        orderEntity.setStatus(OrderStatus.PAGO);

        OrderEntity orderPaid = orderRepository.save(orderEntity);

//        emailService.sendNote();

        return ConversorMapper.converter(orderPaid, OrderOutputDTO.class);

    }
}
