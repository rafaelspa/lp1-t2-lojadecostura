package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query(
            value = "SELECT * FROM clientes c WHERE c.email = ?1",
            nativeQuery = true)
    Cliente findByEmail(String email);
    @Query(
            value = "SELECT * FROM clientes c WHERE c.nome = ?1",
            nativeQuery = true)
    Cliente findByNome(String nome);
}
