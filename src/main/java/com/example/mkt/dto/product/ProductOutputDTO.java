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

    private Integer idProduct;

    private String nameProduct;

    private Double price;

    private String description;

    public ProductOutputDTO(ProductEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
