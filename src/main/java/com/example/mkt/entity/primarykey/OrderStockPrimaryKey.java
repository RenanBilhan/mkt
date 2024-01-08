package com.example.mkt.entity.primarykey;

import com.example.mkt.entity.StockEntity;
import com.example.mkt.entity.OrderEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class OrderStockPrimaryKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "ID_ESTOQUE")
    private StockEntity stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStockPrimaryKey that = (OrderStockPrimaryKey) o;
        return Objects.equals(order, that.order) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, stock);
    }

}
