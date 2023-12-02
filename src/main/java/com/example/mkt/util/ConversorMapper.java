package com.example.mkt.util;

import com.example.mkt.dto.cliente.ClienteOutputDTO;
import com.example.mkt.dto.pedido.PedidoOutputDTO;
import com.example.mkt.dto.pedidoEstoque.PedidoEstoqueInputDTO;
import com.example.mkt.dto.pedidoEstoque.PedidoEstoqueOutputDTO;
import com.example.mkt.dto.usuario.UsuarioOutputDTO;
import com.example.mkt.entity.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

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

    public static PedidoOutputDTO converterPedidoOutputDTO(PedidoEntity pedido){
        PedidoOutputDTO pedidoOutputDTO = ConversorMapper.converter(pedido, PedidoOutputDTO.class);

        pedidoOutputDTO.setNomeCliente(pedido.getCliente().getNomeCliente());
        List<PedidoEstoqueOutputDTO> pedidoEstoqueOutputDTOList = pedido.getItens()
                .stream().map(ConversorMapper::convertPedidoEstoqueToOutput).toList();
        pedidoOutputDTO.setItens(pedidoEstoqueOutputDTOList);

        return pedidoOutputDTO;
    }

    public static UsuarioOutputDTO converterUsuarioParaDTO(UsuarioEntity entity){
        UsuarioOutputDTO retorno =  objectMapper.convertValue(entity, UsuarioOutputDTO.class);

        retorno.setCargo(entity.getCargos());
        return retorno;
    }

    public static ClienteOutputDTO converterClienteParaDTO(ClienteEntity entity){

        ClienteOutputDTO retorno = new ClienteOutputDTO();

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

    public static PedidoEstoqueOutputDTO convertPedidoEstoqueToOutput(PedidoEstoqueEntity entity){
        PedidoEstoqueOutputDTO pedidoEstoqueOutputDTO = ConversorMapper.converter(entity, PedidoEstoqueOutputDTO.class);

        pedidoEstoqueOutputDTO.setIdPedido(entity.getPedido().getIdPedido());
        pedidoEstoqueOutputDTO.setIdProduto(entity.getEstoque().getIdEstoque());

        return pedidoEstoqueOutputDTO;
    }


}
