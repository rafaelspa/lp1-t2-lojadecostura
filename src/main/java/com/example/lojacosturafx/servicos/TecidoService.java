package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Tecido;
import com.example.lojacosturafx.repositorios.TecidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TecidoService {
    private TecidoRepository tecidoRepository;

    public void create(Tecido tecido) {
        tecidoRepository.save(tecido);
    }

    public Optional<Tecido> findById(Long id) {
        return tecidoRepository.findById(id);
    }

    public List<Tecido> findAll() {
        return tecidoRepository.findAll();
    }

    public void delete(Long id) {
        tecidoRepository.deleteById(id);
    }

    public void update(Long id, String nome, Double precoMetro) throws Exception {
        Tecido tecido = tecidoRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe esse aecido")
        );

        tecido.nome = nome;
        tecido.precoMetro = precoMetro;
        tecidoRepository.save(tecido);
    }

    public Tecido findByNome(String nome) {
        return tecidoRepository.findByNome(nome);
    }
}