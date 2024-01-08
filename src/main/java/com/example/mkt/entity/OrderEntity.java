package com.example.mkt.entity;

import com.example.mkt.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "PEDIDO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
    @SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "seq_pedido", allocationSize = 1)
    @Column(name = "ID_PEDIDO")
    private Integer idOrder;

    @Column(name = "FRETE")
    private Double freight;

    @Column(name = "PRECO_TOTAL_PRODUTOS")
    private Double priceTotalProducts;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ID_PAGAMENTO_STRIPE")
    private String idStripePayment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    private ClientEntity client;

    @JsonIgnore
    @OneToMany(mappedBy = "pedidoEstoquePK.pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderStockEntity> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(getIdOrder(), that.getIdOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrder());
    }

}
