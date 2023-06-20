package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {
    @Query(
            value = "SELECT * FROM pecas p WHERE p.nome = ?1",
            nativeQuery = true)
    Peca findByNome(String nome);
}
