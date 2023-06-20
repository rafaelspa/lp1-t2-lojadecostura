package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.ItemPedido;
import com.example.lojacosturafx.entidades.Orcamento;
import com.example.lojacosturafx.repositorios.OrcamentoRepository;
import com.example.lojacosturafx.repositorios.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrcamentoService {
    private OrcamentoRepository orcamentoRepository;
    private PedidoRepository pedidoRepository;

    public List<Orcamento> findAll() {
        return orcamentoRepository.findAll();
    }

    public void create(Orcamento orcamento) {
        orcamentoRepository.save(orcamento);
    }

    public void update(Long id, Long usuarioId, Long clienteId, String observacoes) throws Exception {
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe esse orcamento")
        );

        orcamento.setUsuarioId(usuarioId);
        orcamento.setClienteId(clienteId);
        orcamento.setObservacoes(observacoes);
        orcamentoRepository.save(orcamento);
    }

    public void delete(Orcamento orcamento) {
        orcamentoRepository.delete(orcamento);
    }

    public void addItem(Long id, ItemPedido itemPedido) throws Exception {
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(
                () -> new Exception("O orçamento não foi encontrado")
        );
        orcamento.getItensPedido().add(itemPedido);
        orcamentoRepository.save(orcamento);
    }

    public void confirma(Long id) throws Exception {
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(
                () -> new Exception("O orçamento não foi encontrado")
        );
        orcamento.setConfirmado(Boolean.TRUE);
        orcamentoRepository.save(orcamento);
    }

    public void desconfirmar(Long orcamentoId) throws Exception {
        Orcamento orcamento = orcamentoRepository.findById(orcamentoId).orElseThrow(
                () -> new Exception("O orçamento não foi encontrado")
        );
        orcamento.setConfirmado(Boolean.FALSE);
        orcamentoRepository.save(orcamento);
    }

    public Optional<Orcamento> findById(Long orcamentoId) {
        return orcamentoRepository.findById(orcamentoId);
    }
}
