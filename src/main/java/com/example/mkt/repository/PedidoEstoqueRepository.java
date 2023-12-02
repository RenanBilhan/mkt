package com.example.mkt.repository;

import com.example.mkt.entity.PedidoEstoqueEntity;
import com.example.mkt.entity.pk.PedidoEstoquePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoEstoqueRepository extends JpaRepository<PedidoEstoqueEntity, PedidoEstoquePK> {
}
