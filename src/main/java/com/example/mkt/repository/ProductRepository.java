package com.example.mkt.repository;

import com.example.mkt.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query("SELECT p FROM PRODUTO p WHERE LOWE(p.nameProduct) LIKE LOWER('%nameProduct%')")
    ProductEntity findByNameProduct(@Param("nameParent") String nameProduct);
}
