package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Modelo;
import com.example.lojacosturafx.entidades.Peca;
import com.example.lojacosturafx.repositorios.ModeloRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModeloService {
    private ModeloRepository modeloRepository;

    public void create(Modelo modelo) {
        modeloRepository.save(modelo);
    }

    public Optional<Modelo> findById(Long id) {
        return modeloRepository.findById(id);
    }

    public List<Modelo> findAll() {
        return modeloRepository.findAll();
    }

    public void delete(Long id) {
        modeloRepository.deleteById(id);
    }

    public void update(Long id, String nome, Double multiplicador) throws Exception {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe esse modelo")
        );

        modelo.setNome(nome);
        modelo.setMultiplicador(multiplicador);
        modeloRepository.save(modelo);
    }

    public void addPeca(Long id, Peca peca) throws Exception {
        Modelo modelo = modeloRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe esse modelo")
        );
        modelo.getPecas().add(peca);
        modeloRepository.save(modelo);
    }

    public Modelo findByNome(String nome) {
        return modeloRepository.findByNome(nome);
    }
}
