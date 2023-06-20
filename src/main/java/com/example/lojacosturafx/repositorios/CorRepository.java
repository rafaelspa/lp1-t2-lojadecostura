package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Cor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {
    @Query(
            value = "SELECT * FROM cores c WHERE c.hex_code = ?1",
            nativeQuery = true)
    Cor findByHexCode(String hexCode);
}
