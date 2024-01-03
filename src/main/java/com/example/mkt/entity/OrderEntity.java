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
    private Integer idPedido;

    @Column(name = "FRETE")
    private Double frete;

    @Column(name = "PRECO_TOTAL_PRODUTOS")
    private Double precoTotalProdutos;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ID_PAGAMENTO_STRIPE")
    private String idPagamentoStripe;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    private ClientEntity cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "pedidoEstoquePK.pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderStockEntity> itens;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(getIdPedido(), that.getIdPedido());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPedido());
    }

}
