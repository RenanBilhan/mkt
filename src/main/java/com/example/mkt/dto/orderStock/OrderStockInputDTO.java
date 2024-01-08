package com.example.mkt.dto.orderStock;

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
public class OrderStockInputDTO {

    @NotNull
    @Positive
    @Schema(description = "Stock identificator", required = true)
    private Integer idStock;

    @NotNull
    @Positive
    @Schema(description = "Amount to be purchased", required = true)
    private Integer amountOfBuying;
}
