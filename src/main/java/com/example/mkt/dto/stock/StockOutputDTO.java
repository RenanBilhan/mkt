package com.example.mkt.dto.stock;

import com.example.mkt.entity.StockEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockOutputDTO {

    private Integer idEstoque;

    private String tamanho;

    private Integer quantidade;

    private Integer idProduto;

    public StockOutputDTO(StockEntity entity) {
        this.idEstoque = entity.getIdEstoque();
        this.tamanho = entity.getTamanho();
        this.quantidade = entity.getQuantidade();
        this.idProduto = entity.getProduto().getIdProduto();
    }
}
