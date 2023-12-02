package com.example.mkt.repository;

import com.example.mkt.entity.CargoEntity;
import com.example.mkt.entity.enums.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
    public Optional<CargoEntity> findByNome(String nome);
}
