package com.example.lojacosturafx.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "adicionais")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Adicional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public  String nome;
    public Double multiplicador;

    public Adicional(String nome, Double multiplicador) {
        this.nome = nome;
        this.multiplicador = multiplicador;
    }
}
