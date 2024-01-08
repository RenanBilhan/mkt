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

    @Schema(description = "Product's name", required = true)
    private String nameProduct;

    @Schema(description = "Product's price", required = true)
    private Double price;

    @Schema(description = "Product's description", required = true)
    private String description;

}
