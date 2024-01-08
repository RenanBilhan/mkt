package com.example.mkt.entity;

import com.example.mkt.entity.primarykey.OrderStockPrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PEDIDO_ESTOQUE")
public class OrderStockEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderStockPrimaryKey orderStockPK = new OrderStockPrimaryKey();

    @Setter
    @Getter
    @Column(name = "QUANTIDADE")
    private Integer quantity;

    @Getter
    @Setter
    @Column(name = "TAMANHO")
    private String size;

    @Setter
    @Getter
    @Column(name = "PRECO")
    private Double price;

    public OrderStockEntity(OrderEntity pedido, StockEntity estoque,
                            Integer quantity, Double price, String size) {
        super();
        orderStockPK.setOrder(pedido);
        orderStockPK.setStock(estoque);
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    @JsonIgnore
    public OrderEntity getPedido(){
        return orderStockPK.getOrder();
    }

    public void setStock(OrderEntity pedido){
        orderStockPK.setOrder(pedido);
    }

    public StockEntity getEstoque(){
        return orderStockPK.getStock();
    }

    public void setEstoque(StockEntity estoque){
        orderStockPK.setStock(estoque);
    }

    public Double getSubtotal(){
        return this.price * this.quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStockEntity pedidoProdutoDisponivel = (OrderStockEntity) o;
        return Objects.equals(orderStockPK, pedidoProdutoDisponivel.orderStockPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStockPK);
    }
}
