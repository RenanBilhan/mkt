package com.example.mkt.dto.produto;

import com.example.mkt.entity.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoOutputDTO {

    private Integer idProduto;

    private String nomeProduto;

    private Double preco;

    private String descricao;

    public ProdutoOutputDTO(ProdutoEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
