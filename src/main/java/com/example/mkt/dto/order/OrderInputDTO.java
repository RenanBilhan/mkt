package com.example.mkt.dto.order;

import com.example.mkt.dto.orderStock.OrderStockInputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderInputDTO {

    private List<OrderStockInputDTO> items;
}
