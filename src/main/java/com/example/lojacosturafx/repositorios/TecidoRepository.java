package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Tecido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TecidoRepository extends JpaRepository<Tecido, Long> {
    @Query(
            value = "SELECT * FROM tecidos t WHERE t.nome = ?1",
            nativeQuery = true)
    Tecido findByNome(String nome);
}
