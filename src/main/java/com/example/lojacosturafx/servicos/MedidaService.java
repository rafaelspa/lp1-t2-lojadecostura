package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Medida;
import com.example.lojacosturafx.repositorios.MedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedidaService {
    private MedidaRepository medidaRepository;

    public void create(Medida medida) {
        medidaRepository.save(medida);
    }


    public void update(Long id, String nome, Double tamanho) throws Exception {
        Medida medida = medidaRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe essa medida")
        );

        medida.setNome(nome);
        medida.setTamanho(tamanho);
        medidaRepository.save(medida);
    }

    public void delete(Long id) {
        medidaRepository.deleteById(id);
    }
}
