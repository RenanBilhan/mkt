package com.example.mkt.dto.pedido;

import com.example.mkt.dto.pedidoEstoque.PedidoEstoqueInputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoInputDTO {

    private List<PedidoEstoqueInputDTO> itens;
}
