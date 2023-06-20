package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Adicional;
import com.example.lojacosturafx.repositorios.AdicionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdicionalService {
    private AdicionalRepository adicionalRepository;

    public void create(Adicional adicional) {
        adicionalRepository.save(adicional);
    }

    public Optional<Adicional> findById(Long id) {
        return adicionalRepository.findById(id);
    }

    public List<Adicional> findAll() {
        return adicionalRepository.findAll();
    }

    public void delete(Long id) {
        adicionalRepository.deleteById(id);
    }

    public void update(Long id, String nome, Double multiplicador) throws Exception {
        Adicional adicional = adicionalRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe esse adicional")
        );

        adicional.nome = nome;
        adicional.multiplicador = multiplicador;
        adicionalRepository.save(adicional);
    }
}
