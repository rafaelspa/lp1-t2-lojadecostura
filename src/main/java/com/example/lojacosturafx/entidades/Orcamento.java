package com.example.lojacosturafx.entidades;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "orcamentos")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter

public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    private Long usuarioId;
    private Long clienteId;
    private Date dataCriacao;
    @OneToMany(fetch = FetchType.EAGER)
    private List<ItemPedido> itensPedido;
    private Double valorTotal;
    private String observacoes;
    private Boolean confirmado;

    public Orcamento(Long usuarioId, Long clienteId, String observacoes) {
        this.usuarioId = usuarioId;
        this.clienteId = clienteId;
        this.observacoes = observacoes == null ? observacoes : "";
        this.dataCriacao = new Date();
        this.confirmado = Boolean.FALSE;
        this.valorTotal = 0.0d;
    }
}
