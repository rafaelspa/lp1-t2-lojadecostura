package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Medida;
import com.example.lojacosturafx.entidades.Peca;
import com.example.lojacosturafx.repositorios.PecaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PecaService {
    private PecaRepository pecaRepository;

    public Peca create(Peca peca) {
        return pecaRepository.save(peca);
    }

    public void update(Long id, String nome, Double precoBase) throws Exception {
        Peca peca = pecaRepository.findById(id).orElseThrow(
                () -> new Exception("Peca não encontrada")
        );
        peca.setNome(nome);
        peca.setPrecoBase(precoBase);
        pecaRepository.save(peca);
    }

    public void delete(Long id) {
        pecaRepository.deleteById(id);
    }

    public List<Peca> findAll() {
        return pecaRepository.findAll();
    }

    public void addMedida(Long id, Medida medida) throws Exception {
        Peca peca = pecaRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe essa peça")
        );
        peca.getMedidas().add(medida);
        pecaRepository.save(peca);
    }

    public Peca findByNome(String nome) {
        return pecaRepository.findByNome(nome);
    }
}
