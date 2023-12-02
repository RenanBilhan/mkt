package com.example.mkt.dto.estoque;

import com.example.mkt.entity.EstoqueEntity;
import com.example.mkt.entity.PedidoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueOutputDTO {

    private Integer idEstoque;

    private String tamanho;

    private Integer quantidade;

    private Integer idProduto;

    public EstoqueOutputDTO(EstoqueEntity entity) {
        this.idEstoque = entity.getIdEstoque();
        this.tamanho = entity.getTamanho();
        this.quantidade = entity.getQuantidade();
        this.idProduto = entity.getProduto().getIdProduto();
    }
}
