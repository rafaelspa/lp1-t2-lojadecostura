package com.example.lojacosturafx.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modelos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double multiplicador;

    @OneToMany(fetch = FetchType.EAGER)
    List<Peca> pecas = new ArrayList<>();

    public Modelo(String nome, Double multiplicador) {
        this.nome = nome;
        this.multiplicador = multiplicador;
    }
}
