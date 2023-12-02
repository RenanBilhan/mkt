package com.example.mkt.entity;

import com.example.mkt.entity.pk.PedidoEstoquePK;
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
public class PedidoEstoqueEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PedidoEstoquePK pedidoEstoquePK = new PedidoEstoquePK();

    @Setter
    @Getter
    @Column(name = "QUANTIDADE")
    private Integer quantidade;

    @Getter
    @Setter
    @Column(name = "TAMANHO")
    private String tamanho;

    @Setter
    @Getter
    @Column(name = "PRECO")
    private Double preco;

    public PedidoEstoqueEntity(PedidoEntity pedido, EstoqueEntity estoque,
                                         Integer quantidade, Double preco, String tamanho) {
        super();
        pedidoEstoquePK.setPedido(pedido);
        pedidoEstoquePK.setEstoque(estoque);
        this.quantidade = quantidade;
        this.preco = preco;
        this.tamanho = tamanho;
    }

    @JsonIgnore
    public PedidoEntity getPedido(){
        return pedidoEstoquePK.getPedido();
    }

    public void setPedido(PedidoEntity pedido){
        pedidoEstoquePK.setPedido(pedido);
    }

    public EstoqueEntity getEstoque(){
        return pedidoEstoquePK.getEstoque();
    }

    public void setEstoque(EstoqueEntity estoque){
        pedidoEstoquePK.setEstoque(estoque);
    }

    public Double getSubtotal(){
        return this.preco * this.quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoEstoqueEntity pedidoProdutoDisponivel = (PedidoEstoqueEntity) o;
        return Objects.equals(pedidoEstoquePK, pedidoProdutoDisponivel.pedidoEstoquePK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedidoEstoquePK);
    }
}
