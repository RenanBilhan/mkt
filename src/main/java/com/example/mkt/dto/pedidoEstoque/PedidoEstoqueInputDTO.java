package com.example.mkt.dto.pedidoEstoque;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEstoqueInputDTO {

    @NotNull
    @Positive
    @Schema(description = "Identificador do produto disponível", required = true)
    private Integer idEstoque;

    @NotNull
    @Positive
    @Schema(description = "Quantidade de unidades do produto que serão compradas", required = true)
    private Integer quantidadeDeCompra;
}
