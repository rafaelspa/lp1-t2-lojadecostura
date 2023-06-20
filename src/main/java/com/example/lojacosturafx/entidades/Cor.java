package com.example.lojacosturafx.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cores")
@NoArgsConstructor
@Getter
@Setter

public class Cor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hexCode;
    private Integer redValue;
    private Integer greenValue;
    private Integer blueValue;
    //cor é definida por valores RGB que vão de 0 a 255, e pode ser descrita pelo hexCode resultante.

    public Cor(String hexCode, Integer redValue, Integer greenValue, Integer blueValue){
        this.hexCode = hexCode;
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }
}