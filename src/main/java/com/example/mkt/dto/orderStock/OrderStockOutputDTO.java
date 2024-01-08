package com.example.mkt.dto.orderStock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStockOutputDTO {

    private Integer idOrder;

    private Integer idProduct;

    private Integer quantity;

    private String size;

    private Double price;
}
