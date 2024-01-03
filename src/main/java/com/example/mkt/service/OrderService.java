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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository pedidoRepository;

    private final ClientRepository clienteRepository;

    private final StockRepository estoqueRepository;

    private final EmailService emailService;

    private final OrderStockRepository pedidoEstoqueRepository;

    public List<OrderOutputDTO> findAll(){
        List<ItemOutputDTO> items = new ArrayList<>();
        return pedidoRepository.findAll().stream()
                .map(OrderOutputDTO::new)
                .collect(Collectors.toList());
    }

    public OrderOutputDTO save(Integer idCliente, OrderInputDTO pedidoInputDTO) throws BussinessRuleException, MessagingException {

        List<OrderStockInputDTO> itens = pedidoInputDTO.getItens();

        ClientEntity cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntitiesNotFoundException("Cliente não encontrado."));

        OrderEntity novoPedido = new OrderEntity();

        novoPedido.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        novoPedido.setFrete(0.0);
        novoPedido.setPrecoTotalProdutos(0.0);
        novoPedido.setCliente(cliente);

        OrderEntity pedidoCriado = pedidoRepository.save(novoPedido);

        List<OrderStockEntity> listaPedidoEstoque = new ArrayList<>();

        for(OrderStockInputDTO item : itens) {


            Double valorAtual = novoPedido.getPrecoTotalProdutos();

            StockEntity estoque = estoqueRepository.findById(item.getIdEstoque())
                            .orElseThrow(() -> new EntitiesNotFoundException("Estoque não registrado."));

            OrderStockEntity pedidoEstoqueEntity = new OrderStockEntity(
                    novoPedido,
                    estoque,
                    item.getQuantidadeDeCompra(),
                    estoque.getProduto().getPreco()*item.getQuantidadeDeCompra(),
                    estoque.getTamanho()
            );

            listaPedidoEstoque.add(pedidoEstoqueEntity);
            novoPedido.setItens(listaPedidoEstoque);
            novoPedido.setPrecoTotalProdutos(valorAtual += pedidoEstoqueEntity.getPreco());

        }

        novoPedido.setItens(listaPedidoEstoque);

        OrderEntity pedidoSalvo = pedidoRepository.save(novoPedido);

        return ConversorMapper.converterPedidoOutputDTO(pedidoSalvo);
    }

    public OrderOutputDTO confirmPayment(Integer idPedido) throws MessagingException {
        Optional<OrderEntity> pedidoEntityOptional = pedidoRepository.findById(idPedido);

        if(pedidoEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Pedido não encontrado.");
        }

        OrderEntity pedidoPagar = pedidoEntityOptional.get();

        pedidoPagar.setStatus(OrderStatus.PAGO);

        OrderEntity pedidoPago = pedidoRepository.save(pedidoPagar);

        emailService.sendNote();

        return ConversorMapper.converter(pedidoPago, OrderOutputDTO.class);

    }
}
