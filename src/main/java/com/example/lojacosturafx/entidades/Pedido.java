package com.example.lojacosturafx.entidades;

import com.example.lojacosturafx.enums.Situacao;
import com.example.lojacosturafx.enums.TipoPagamento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class Pedido extends Orcamento {
    @Setter(AccessLevel.NONE)
    private Long id;
    private LocalDate dataEntrega;
    private Boolean pago;
    private LocalDate dataPagamento;
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    private Long orcamento;

    public Pedido(Long orcamento) {
        this.orcamento = orcamento;
        this.situacao = Situacao.CRIADO;
    }
}
