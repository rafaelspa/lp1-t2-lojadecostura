package com.example.lojacosturafx.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "usuarios", uniqueConstraints={
        @UniqueConstraint(columnNames={"nomeUsuario", "emailUsuario"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nomeUsuario", unique = true)
    private String nomeUsuario;
    @Column(name = "emailUsuario", unique = true)
    private String emailUsuario;
    private String senhaUsuario;

    public Usuario(String nomeUsuario, String emailUsuario, String senhaUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.senhaUsuario = senhaUsuario;
    }
}