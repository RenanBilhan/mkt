package com.example.mkt.util;

import com.example.mkt.dto.client.ClientOutputDTO;
import com.example.mkt.dto.order.ItemOutputDTO;
import com.example.mkt.dto.order.OrderOutputDTO;
import com.example.mkt.dto.orderStock.OrderStockOutputDTO;
import com.example.mkt.dto.user.UserOutputDTO;
import com.example.mkt.entity.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class ConversorMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    public static <TipoEntrada, TipoSaida> TipoSaida converter(TipoEntrada entrada, Class<TipoSaida> saida){
        return objectMapper.convertValue(entrada, saida);
    }

    public static OrderOutputDTO converterPedidoOutputDTO(OrderEntity pedido){
        OrderOutputDTO pedidoOutputDTO = ConversorMapper.converter(pedido, OrderOutputDTO.class);

        pedidoOutputDTO.setNomeCliente(pedido.getCliente().getNomeCliente());
        List<OrderStockOutputDTO> pedidoEstoqueOutputDTOList = pedido.getItens()
                .stream().map(ConversorMapper::convertPedidoEstoqueToOutput).toList();
        pedidoOutputDTO.setItens(ConversorMapper.convertItensToItemOutputDTO(pedido.getItens()));

        return pedidoOutputDTO;
    }

    public static UserOutputDTO converterUsuarioParaDTO(UserEntity entity){
        UserOutputDTO retorno =  objectMapper.convertValue(entity, UserOutputDTO.class);

        retorno.setCargo(entity.getCargos());
        return retorno;
    }

    public static ClientOutputDTO converterClienteParaDTO(ClientEntity entity){

        ClientOutputDTO retorno = new ClientOutputDTO();

        retorno.setIdCliente(entity.getIdCliente());
        retorno.setGenero(entity.getGenero());
        retorno.setNomeCliente(entity.getNomeCliente());
        retorno.setEmailCliente(entity.getEmailCliente());
        retorno.setCpf(entity.getCpf());
        retorno.setDataNascimento(entity.getDataNascimento());
        retorno.setTelefone(entity.getTelefone());
        retorno.setEnderecos(entity.getEnderecos());

        return retorno;
    }

    public static OrderStockOutputDTO convertPedidoEstoqueToOutput(OrderStockEntity entity){
        OrderStockOutputDTO pedidoEstoqueOutputDTO = ConversorMapper.converter(entity, OrderStockOutputDTO.class);

        pedidoEstoqueOutputDTO.setIdPedido(entity.getPedido().getIdPedido());
        pedidoEstoqueOutputDTO.setIdProduto(entity.getEstoque().getIdEstoque());

        return pedidoEstoqueOutputDTO;
    }

    public static List<ItemOutputDTO> convertItensToItemOutputDTO(List<OrderStockEntity> items){

        List<ItemOutputDTO> outputDTO = new ArrayList<>();

        for(OrderStockEntity item : items){
            ItemOutputDTO itemToAdd = new ItemOutputDTO();
            itemToAdd.setIdProduto(item.getPedidoEstoquePK().getEstoque().getProduto().getIdProduto());
            itemToAdd.setQuantity(item.getQuantidade());
            itemToAdd.setNomeProduto(item.getPedidoEstoquePK().getEstoque().getProduto().getNomeProduto());
            itemToAdd.setProductsPrice(item.getPedidoEstoquePK().getEstoque().getProduto().getPreco()* itemToAdd.getQuantity());
            outputDTO.add(itemToAdd);
        }
        return outputDTO;
    }


}
