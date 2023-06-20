package com.example.lojacosturafx.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tecidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Tecido{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String nome;
    public Double precoMetro;

    public Tecido(String nome, Double precoMetro){
        this.nome = nome;
        this.precoMetro = precoMetro;
    }
}