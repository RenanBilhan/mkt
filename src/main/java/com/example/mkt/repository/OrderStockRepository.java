package com.example.mkt.repository;

import com.example.mkt.entity.OrderStockEntity;
import com.example.mkt.entity.primarykey.OrderStockPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStockRepository extends JpaRepository<OrderStockEntity, OrderStockPrimaryKey> {
}
