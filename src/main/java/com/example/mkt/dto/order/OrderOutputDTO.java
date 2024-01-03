package com.example.mkt.dto.order;

import com.example.mkt.entity.OrderEntity;
import com.example.mkt.util.ConversorMapper;
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
public class OrderOutputDTO {

    private Integer idPedido;

    private String nomeCliente;

    private Double frete;

    private List<ItemOutputDTO> itens;

    private Double precoTotalProdutos;

    private String status;

    public OrderOutputDTO(OrderEntity entity) {
        BeanUtils.copyProperties(entity, this);
        this.setItens(ConversorMapper.convertItensToItemOutputDTO(entity.getItens()));
        this.setNomeCliente(entity.getCliente().getNomeCliente());
        this.setStatus(entity.getStatus().toString());
    }
}
