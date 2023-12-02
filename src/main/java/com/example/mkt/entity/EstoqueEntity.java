package com.example.mkt.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ESTOQUE")
public class EstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTOQUE_SEQ")
    @SequenceGenerator(name = "ESTOQUE_SEQ", sequenceName = "SEQ_ESTOQUE", allocationSize = 1)
    @Column(name = "ID_ESTOQUE")
    private Integer idEstoque;

    private String tamanho;

    private Integer quantidade;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO")
    private ProdutoEntity produto;

    @JsonIgnore
    @OneToMany(mappedBy = "pedidoEstoquePK.estoque", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PedidoEstoqueEntity> pedidos;

    @JsonIgnore
    public List<PedidoEntity> getListaPedidoEntity(){
        List<PedidoEntity> lista = new ArrayList<>();
        for (PedidoEstoqueEntity x : pedidos){
            lista.add(x.getPedido());
        }
        return lista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstoqueEntity that = (EstoqueEntity) o;
        return Objects.equals(getIdEstoque(), that.getIdEstoque());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdEstoque());}
}
