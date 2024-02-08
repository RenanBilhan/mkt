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

    @NotNull(message = "Product must have a name(null)")
    @NotBlank(message = "Product must have a name(blank)")
    @NotEmpty(message = "Product must have a name(empty)")
    @Schema(description = "Product's name", required = true)
    private String nameProduct;

    @Positive(message = "Product must have a valid price")
    @Schema(description = "Product price", required = true)
    private Double price;

    @NotNull(message = "Product must have a description")
    @NotEmpty(message = "Product must have a description")
    @Schema(description = "Product's description", required = true)
    private String description;
}
