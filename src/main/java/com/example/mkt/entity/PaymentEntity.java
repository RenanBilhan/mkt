package com.example.mkt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "PAGAMENTO")
@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAGAMENTO_SEQ")
    @SequenceGenerator(name = "PAGAMENTO_SEQ", sequenceName = "SEQ_PAGAMENTO", allocationSize = 1)
    @Column(name = "ID_PAGAMENTO")
    private Integer idPagamento;

    @Column(name = "ID_PAGAMENTO_STRIPE")
    private String idPagamentoStripe;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PEDIDO", referencedColumnName = "ID_PEDIDO")
    private OrderEntity pedido;


}
