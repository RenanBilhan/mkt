package com.example.mkt.service;

import com.example.mkt.dto.pedido.PedidoInputDTO;
import com.example.mkt.dto.pedido.PedidoOutputDTO;
import com.example.mkt.dto.pedidoEstoque.PedidoEstoqueInputDTO;
import com.example.mkt.entity.*;
import com.example.mkt.entity.enums.StatusPedido;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.repository.*;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ClienteRepository clienteRepository;

    private final EstoqueRepository estoqueRepository;

    private final EmailService emailService;

    private final PedidoEstoqueRepository pedidoEstoqueRepository;

    public List<PedidoOutputDTO> findAll(){
        return pedidoRepository.findAll().stream()
                .map(PedidoOutputDTO::new)
                .collect(Collectors.toList());
    }

    public PedidoOutputDTO save(Integer idCliente, PedidoInputDTO pedidoInputDTO) throws RegraDeNegocioException, MessagingException {

        List<PedidoEstoqueInputDTO> itens = pedidoInputDTO.getItens();

        ClienteEntity cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntitiesNotFoundException("Cliente não encontrado."));

        PedidoEntity novoPedido = new PedidoEntity();

        novoPedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        novoPedido.setFrete(0.0);
        novoPedido.setPrecoTotalProdutos(0.0);
        novoPedido.setCliente(cliente);

        PedidoEntity pedidoCriado = pedidoRepository.save(novoPedido);

        List<PedidoEstoqueEntity> listaPedidoEstoque = new ArrayList<>();

        for(PedidoEstoqueInputDTO item : itens) {


            Double valorAtual = novoPedido.getPrecoTotalProdutos();

            EstoqueEntity estoque = estoqueRepository.findById(item.getIdEstoque())
                            .orElseThrow(() -> new EntitiesNotFoundException("Estoque não registrado."));

            PedidoEstoqueEntity pedidoEstoqueEntity = new PedidoEstoqueEntity(
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

        PedidoEntity pedidoSalvo = pedidoRepository.save(novoPedido);

        return ConversorMapper.converterPedidoOutputDTO(pedidoSalvo);
    }

    public PedidoOutputDTO confirmPayment(Integer idPedido) throws MessagingException {
        Optional<PedidoEntity> pedidoEntityOptional = pedidoRepository.findById(idPedido);

        if(pedidoEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Pedido não encontrado.");
        }

        PedidoEntity pedidoPagar = pedidoEntityOptional.get();

        pedidoPagar.setStatus(StatusPedido.PAGO);

        PedidoEntity pedidoPago = pedidoRepository.save(pedidoPagar);

        emailService.sendNote();

        return ConversorMapper.converter(pedidoPago, PedidoOutputDTO.class);

    }
}
