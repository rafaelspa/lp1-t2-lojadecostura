package com.example.lojacosturafx.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "itens_pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pecaId;
    private String tamanho;
    private Long modeloId;
    private Long tecidoId;
    private Long corId;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Adicional> adicionais;
    private Double valorItem;

    public ItemPedido(Long pecaId, String tamanho, Long modeloId, Long tecidoId, Long corId, Double valorItem) {
        this.pecaId = pecaId;
        this.tamanho = tamanho;
        this.modeloId = modeloId;
        this.tecidoId = tecidoId;
        this.corId = corId;
        this.valorItem = valorItem;
    }
}