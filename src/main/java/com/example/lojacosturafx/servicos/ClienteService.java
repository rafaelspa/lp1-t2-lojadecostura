package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Cliente;
import com.example.lojacosturafx.entidades.Medida;
import com.example.lojacosturafx.repositorios.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {
    private ClienteRepository clienteRepository;

    public void create(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    public void update(Long id, String nome, String telefone, String email) throws Exception {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe esse cliente")
        );

        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        clienteRepository.save(cliente);
    }

    public void addMedida(Long id, Medida medida) throws Exception {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new Exception("Não existe esse cliente")
        );
        cliente.getMedidas().add(medida);
        clienteRepository.save(cliente);
    }

    public Cliente findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }
}
