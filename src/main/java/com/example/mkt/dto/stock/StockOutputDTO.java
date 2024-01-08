package com.example.mkt.dto.stock;

import com.example.mkt.entity.StockEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockOutputDTO {

    private Integer idStock;

    private String size;

    private Integer quantity;

    private Integer idProduct;

    public StockOutputDTO(StockEntity entity) {
        this.idStock = entity.getIdStock();
        this.size = entity.getSize();
        this.quantity = entity.getQuantity();
        this.idProduct = entity.getProduct().getIdProduct();
    }
}
