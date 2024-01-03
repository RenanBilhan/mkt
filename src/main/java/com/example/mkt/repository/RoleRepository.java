package com.example.mkt.repository;

import com.example.mkt.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    public Optional<RoleEntity> findByNome(String nome);
}
