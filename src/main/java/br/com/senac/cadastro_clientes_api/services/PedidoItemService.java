package br.com.senac.cadastro_clientes_api.services;

import br.com.senac.cadastro_clientes_api.entities.*;
import br.com.senac.cadastro_clientes_api.exceptions.NaoFoiPossivelAlterar;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.repository.PedidoItemRepository;
import br.com.senac.cadastro_clientes_api.repository.PedidoRepository;
import br.com.senac.cadastro_clientes_api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoItemService {
    @Autowired
    private PedidoItemRepository pedidoItemRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;



    public PedidoItem criarPedidoItem(PedidoItem pedidoItem) throws RegistroNaoEncontrado {
        Long produtoId = pedidoItem.getProduto().getId();
        Long pedidoId = pedidoItem.getPedido().getId();

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RegistroNaoEncontrado("Produto nao encontrado"));

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegistroNaoEncontrado("numero de pedido noa encontrado"));



        return pedidoItemRepository.save(pedidoItem);


    }


    public PedidoItem atualizarPedidoItem(Long id, PedidoItem pedidoItem) throws RegistroNaoEncontrado {
        PedidoItem pedidoItemResult = pedidoItemRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontrado("item nao encontrado"));

        if(!pedidoItemResult.getProduto().getId().equals(pedidoItem.getProduto().getId())){
            throw new NaoFoiPossivelAlterar("nao pode trocar o id do produto");
        }


        pedidoItemResult.setQuantidade(pedidoItem.getQuantidade());
        pedidoItemResult.setValorUnitario(pedidoItem.getValorUnitario());

        return pedidoItemRepository.save(pedidoItemResult);

    }




}
