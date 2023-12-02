package com.example.mkt.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoUpdateDTO {

    @Schema(description = "Nome do produto", required = true)
    private String nomeProduto;

    @Schema(description = "Preco do produto", required = true)
    private Double preco;

    @Schema(description = "Descricao do produto", required = true)
    private String descricao;

}
