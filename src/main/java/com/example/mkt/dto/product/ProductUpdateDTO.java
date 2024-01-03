package com.example.mkt.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {

    @Schema(description = "Nome do produto", required = true)
    private String nomeProduto;

    @Schema(description = "Preco do produto", required = true)
    private Double preco;

    @Schema(description = "Descricao do produto", required = true)
    private String descricao;

}
