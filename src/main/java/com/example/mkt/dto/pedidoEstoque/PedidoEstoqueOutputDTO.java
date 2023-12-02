package com.example.mkt.dto.pedidoEstoque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoEstoqueOutputDTO {

    private Integer idPedido;

    private Integer idProduto;

    private Integer quantidade;

    private String tamanho;

    private Double preco;
}
