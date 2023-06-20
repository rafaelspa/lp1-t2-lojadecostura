package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Pedido;
import com.example.lojacosturafx.enums.Situacao;
import com.example.lojacosturafx.enums.TipoPagamento;
import com.example.lojacosturafx.repositorios.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {
    private PedidoRepository pedidoRepository;

    public void create(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public void update(Long id, LocalDate dataEntrega, Boolean pago, LocalDate dataPagamento, String tipoPagamento, String situacao) throws Exception {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new Exception("O pedido não foi encontrado")
        );

        pedido.setDataEntrega(dataEntrega);
        pedido.setPago(pago);
        pedido.setDataPagamento(dataPagamento);
        pedido.setTipoPagamento(TipoPagamento.valueOf(tipoPagamento));
        pedido.setSituacao(Situacao.valueOf(situacao));
        pedidoRepository.save(pedido);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public void delete(Pedido pedido) {
        pedidoRepository.delete(pedido);
    }
}
