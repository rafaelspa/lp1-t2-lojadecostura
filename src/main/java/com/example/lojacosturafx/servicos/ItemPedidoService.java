package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Adicional;
import com.example.lojacosturafx.entidades.ItemPedido;
import com.example.lojacosturafx.repositorios.ItemPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemPedidoService {
    private ItemPedidoRepository itemPedidoRepository;

    public void create(ItemPedido itemPedido) {
        itemPedidoRepository.save(itemPedido);
    }

    public void update(Long id, Long pecaId, String tamanho, Long modeloId, Long tecidoId, Long corId, Double valorItem) throws Exception {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(
                () -> new Exception("Item do pedido não encontrado")
        );
        itemPedido.setPecaId(pecaId);
        itemPedido.setTamanho(tamanho);
        itemPedido.setModeloId(modeloId);
        itemPedido.setTecidoId(tecidoId);
        itemPedido.setCorId(corId);
        itemPedido.setValorItem(valorItem);

        itemPedidoRepository.save(itemPedido);
    }

    public void delete(ItemPedido itempedido) {
        itemPedidoRepository.delete(itempedido);
    }

    public void addAdicional(Long id, Adicional adicional) throws Exception {
         ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(
                () -> new Exception("Item do pedido não foi encontrado")
        );
        itemPedido.getAdicionais().add(adicional);
        itemPedidoRepository.save(itemPedido);
    }

    public void atualizarValorItem(Long id, Double resultado) throws Exception {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(
                () -> new Exception("Item do pedido não encontrado")
        );
        itemPedido.setValorItem(resultado);
        itemPedidoRepository.save(itemPedido);
    }
}
