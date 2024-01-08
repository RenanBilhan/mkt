package com.example.mkt.dto.order;

import com.example.mkt.entity.OrderEntity;
import com.example.mkt.util.ConversorMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutputDTO {

    private Integer idOrder;

    private String nameClient;

    private Double freight;

    private List<ItemOutputDTO> items;

    private Double pricecTotalProducts;

    private String status;

    public OrderOutputDTO(OrderEntity entity) {
        BeanUtils.copyProperties(entity, this);
        this.setItems(ConversorMapper.convertItensToItemOutputDTO(entity.getItems()));
        this.setNameClient(entity.getClient().getNameClient());
        this.setStatus(entity.getStatus().toString());
    }
}
