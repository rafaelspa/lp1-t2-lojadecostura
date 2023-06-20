package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    @Query(
            value = "SELECT * FROM modelos m WHERE m.nome = ?1",
            nativeQuery = true)
    Modelo findByNome(String nome);
}
