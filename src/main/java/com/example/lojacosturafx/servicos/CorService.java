package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Cor;
import com.example.lojacosturafx.repositorios.CorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CorService {
    private CorRepository corRepository;

    public List<Cor> findAll() {
        return corRepository.findAll();
    }

    public void create(Cor cor) {
        corRepository.save(cor);
    }

    public void update(Long id, String hexCode, Integer redValue, Integer greenValue, Integer blueValue) throws Exception {
        Cor cor = corRepository.findById(id).orElseThrow(
                () -> new Exception("Cor não foi encontrada")
        );
        cor.setRedValue(redValue);
        cor.setGreenValue(greenValue);
        cor.setBlueValue(blueValue);
        cor.setHexCode(hexCode);
        corRepository.save(cor);
    }

    public void delete(Cor cor) {
        corRepository.delete(cor);
    }

    public Cor findByHexCode(String hexCode) {
        return corRepository.findByHexCode(hexCode);
    }
}
