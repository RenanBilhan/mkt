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
    public static <TipoEntrada, TipoSaida> TipoSaida converter(TipoEntrada entry, Class<TipoSaida> output){
        return objectMapper.convertValue(entry, output);
    }

    public static OrderOutputDTO converterPedidoOutputDTO(OrderEntity order){
        OrderOutputDTO orderOutputDTO = ConversorMapper.converter(order, OrderOutputDTO.class);

        orderOutputDTO.setNameClient(order.getClient().getNameClient());
        List<OrderStockOutputDTO> orderStockOutputDTOList = order.getItems()
                .stream().map(ConversorMapper::convertPedidoEstoqueToOutput).toList();
        orderOutputDTO.setItems(ConversorMapper.convertItensToItemOutputDTO(order.getItems()));

        return orderOutputDTO;
    }

    public static UserOutputDTO converterUsuarioParaDTO(UserEntity entity){
        UserOutputDTO userReturn =  objectMapper.convertValue(entity, UserOutputDTO.class);

        userReturn.setRole(entity.getCargos());
        return userReturn;
    }

    public static ClientOutputDTO converterClienteParaDTO(ClientEntity entity){

        ClientOutputDTO clientReturn = new ClientOutputDTO();

        clientReturn.setIdClient(entity.getIdClient());
        clientReturn.setGender(entity.getGender());
        clientReturn.setNameClient(entity.getNameClient());
        clientReturn.setEmailClient(entity.getEmailClient());
        clientReturn.setCpf(entity.getCpf());
        clientReturn.setDateOfBirth(entity.getDateOfBirth());
        clientReturn.setPhoneNumber(entity.getPhoneNumber());
        clientReturn.setAddresses(entity.getAdresses());

        return clientReturn;
    }

    public static OrderStockOutputDTO convertPedidoEstoqueToOutput(OrderStockEntity entity){
        OrderStockOutputDTO orderStockOutputDTO = ConversorMapper.converter(entity, OrderStockOutputDTO.class);

        orderStockOutputDTO.setIdOrder(entity.getPedido().getIdOrder());
        orderStockOutputDTO.setIdProduct(entity.getEstoque().getIdStock());

        return orderStockOutputDTO;
    }

    public static List<ItemOutputDTO> convertItensToItemOutputDTO(List<OrderStockEntity> items){

        List<ItemOutputDTO> outputDTO = new ArrayList<>();

        for(OrderStockEntity item : items){
            ItemOutputDTO itemToAdd = new ItemOutputDTO();
            itemToAdd.setIdProduct(item.getOrderStockPK().getStock().getProduct().getIdProduct());
            itemToAdd.setQuantity(item.getQuantity());
            itemToAdd.setNameProduct(item.getOrderStockPK().getStock().getProduct().getNameProduct());
            itemToAdd.setProductsPrice(item.getOrderStockPK().getStock().getProduct().getPrice()* itemToAdd.getQuantity());
            outputDTO.add(itemToAdd);
        }
        return outputDTO;
    }


}
