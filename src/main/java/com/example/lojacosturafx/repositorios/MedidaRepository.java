package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Medida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidaRepository extends JpaRepository<Medida, Long> {
}
