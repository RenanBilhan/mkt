package com.example.mkt.entity.pk;

import com.example.mkt.entity.EstoqueEntity;
import com.example.mkt.entity.PedidoEntity;
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
public class PedidoEstoquePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO")
    private PedidoEntity pedido;

    @ManyToOne
    @JoinColumn(name = "ID_ESTOQUE")
    private EstoqueEntity estoque;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoEstoquePK that = (PedidoEstoquePK) o;
        return Objects.equals(pedido, that.pedido) && Objects.equals(estoque, that.estoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, estoque);
    }

}
