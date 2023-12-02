package com.example.mkt.dto.pedido;

import com.example.mkt.dto.pedidoEstoque.PedidoEstoqueOutputDTO;
import com.example.mkt.entity.PedidoEntity;
import com.example.mkt.entity.PedidoEstoqueEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoOutputDTO {

    private Integer idPedido;

    private String nomeCliente;

    private Double frete;

    private List<PedidoEstoqueOutputDTO> itens;

    private Double precoTotalProdutos;

    private String status;

    public PedidoOutputDTO(PedidoEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
