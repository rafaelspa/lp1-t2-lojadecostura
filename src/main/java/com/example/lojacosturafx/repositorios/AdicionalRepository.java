package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Adicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
}
