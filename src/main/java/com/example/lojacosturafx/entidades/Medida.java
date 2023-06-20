package com.example.lojacosturafx.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "medidas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Medida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double tamanho;

    public Medida(String nome, Double tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }
}
