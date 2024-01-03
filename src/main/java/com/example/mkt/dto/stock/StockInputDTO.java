package com.example.mkt.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockInputDTO {

    @NotEmpty(message = "O tamanho deve ser preenchido corretamente.")
    @NotNull(message = "O tamanho deve ser preenchido corretamente.")
    @NotBlank(message = "O tamanho deve ser preenchido corretamente.")
    @Size(min = 1, message = "O tamanho deve ser preenchido corretamente.")
    private String tamanho;

    @NumberFormat
    @Size(min = 1, message = "Quantidade deve ser inserida corretamente.")
    private Integer quantidade;
}
