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

    @NotEmpty(message = "Size must be filled correctly.")
    @NotNull(message = "Size must be filled correctly.")
    @NotBlank(message = "Size must be filled correctly.")
    @Size(min = 1, message = "Size must be filled correctly.")
    private String size;

    @NumberFormat
    @Size(min = 1, message = "Quantity must be filled correctly.")
    private Integer quantity;
}
