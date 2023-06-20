package com.example.lojacosturafx.enums;

import lombok.Getter;

@Getter
public enum Tamanho {
    pp("pp", 0.0),
    p("p", 0.02),
    m("m", 0.04),
    g("g", 0.06),
    gg("gg", 0.08);

    public final String sigla;
    public final Double multiplicador;

    Tamanho(String sigla, Double multiplicador) {
        this.sigla = sigla;
        this.multiplicador = multiplicador;
    }
}
