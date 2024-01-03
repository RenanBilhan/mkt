package com.example.mkt.dto.product;

import com.example.mkt.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOutputDTO {

    private Integer idProduto;

    private String nomeProduto;

    private Double preco;

    private String descricao;

    public ProductOutputDTO(ProductEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
