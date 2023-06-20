package com.example.lojacosturafx.repositorios;

import com.example.lojacosturafx.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(
            value = "SELECT * FROM usuarios u WHERE u.email_usuario = ?1",
            nativeQuery = true)
    Usuario findByEmail(String email);
}