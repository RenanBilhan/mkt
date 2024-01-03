package com.example.mkt.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemOutputDTO {

    private Integer idProduto;
    private String nomeProduto;
    private Integer Quantity;
    private Double productsPrice;

}
