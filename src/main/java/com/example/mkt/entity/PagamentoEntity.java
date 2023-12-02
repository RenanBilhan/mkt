package com.example.mkt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "PAGAMENTO")
@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAGAMENTO_SEQ")
    @SequenceGenerator(name = "PAGAMENTO_SEQ", sequenceName = "SEQ_PAGAMENTO", allocationSize = 1)
    @Column(name = "ID_PAGAMENTO")
    private Integer idPagamento;


}
