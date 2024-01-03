package com.example.mkt.dto.product;

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
public class ProductInputDTO {

    @NotNull(message = "Produto deve ter um nome(null)")
    @NotBlank(message = "Produto deve ter um nome(blank)")
    @NotEmpty(message = "Produto deve ter um nome(empty)")
    @Schema(description = "Nome do produto", required = true)
    private String nomeProduto;

    @Positive(message = "Preco deve ser descrito")
    @Schema(description = "Preco do produto", required = true)
    private Double preco;

    @NotNull(message = "Produto deve ter uma descricao")
    @NotEmpty(message = "Produto deve ter uma descricao")
    @Schema(description = "Descricao do produto", required = true)
    private String descricao;
}
