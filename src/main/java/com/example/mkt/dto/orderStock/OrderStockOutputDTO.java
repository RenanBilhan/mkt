package com.example.mkt.dto.orderStock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStockOutputDTO {

    private Integer idPedido;

    private Integer idProduto;

    private Integer quantidade;

    private String tamanho;

    private Double preco;
}
