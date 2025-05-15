package br.com.senac.cadastro_clientes_api.services;

import br.com.senac.cadastro_clientes_api.entities.*;
import br.com.senac.cadastro_clientes_api.exceptions.NaoFoiPossivelAlterar;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.repository.PedidoItemRepository;
import br.com.senac.cadastro_clientes_api.repository.PedidoRepository;
import br.com.senac.cadastro_clientes_api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.senac.cadastro_clientes_api.utils.ValidacoesUtil.validarSeRegistroExiste;

@Service
public class PedidoItemService {
    @Autowired
    private PedidoItemRepository pedidoItemRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;



    public PedidoItem criarPedidoItem(PedidoItem pedidoItem) throws RegistroNaoEncontrado {
//        Long produtoId = pedidoItem.getProduto().getId();
//        Long pedidoId = pedidoItem.getPedido().getId();
//
//        Produto produto = produtoRepository.findById(produtoId)
//                .orElseThrow(() -> new RegistroNaoEncontrado("Produto nao encontrado"));
//
//        Pedido pedido = pedidoRepository.findById(pedidoId)
//                .orElseThrow(() -> new RegistroNaoEncontrado("numero de pedido noa encontrado"));
//
//
//
//        return pedidoItemRepository.save(pedidoItem);


        if(!produtoRepository.existsById(pedidoItem.getProduto().getId())){
            throw new RegistroNaoEncontrado("Produto nao encontrado");
        }

        if(!pedidoRepository.existsById(pedidoItem.getPedido().getId())) {
            throw new RegistroNaoEncontrado("Numero de pedido nao encontrado");
        }

   //valida se tem outro item com o memo produto

        Optional<PedidoItem> resultBuscaPedidoItem = pedidoItemRepository.findByPedidoIdAndProdutoId(
                pedidoItem.getPedido().getId(),
                pedidoItem.getProduto().getId());
        if(resultBuscaPedidoItem.isPresent()){
            throw new RuntimeException("Item ja cadastrado");
        }

    //zera o id para garantir que o banco gere o id
        pedidoItem.setId(null);

        return pedidoItemRepository.save(pedidoItem);


    }


    public PedidoItem atualizarPedidoItem(Long id, PedidoItem pedidoItem) throws RegistroNaoEncontrado {
//        PedidoItem pedidoItemResult = pedidoItemRepository.findById(id)
//                .orElseThrow(() -> new RegistroNaoEncontrado("item nao encontrado"));
//
//        if(!pedidoItemResult.getProduto().getId().equals(pedidoItem.getProduto().getId())){
//            throw new NaoFoiPossivelAlterar("nao pode trocar o id do produto");
//        }
//
//
//        pedidoItemResult.setQuantidade(pedidoItem.getQuantidade());
//        pedidoItemResult.setValorUnitario(pedidoItem.getValorUnitario());
//
//        return pedidoItemRepository.save(pedidoItemResult);


        validarSeRegistroExiste(pedidoItemRepository, id);

        return pedidoItemRepository.findById(id).map(record -> {
            record.setQuantidade(pedidoItem.getQuantidade());
            record.setValorUnitario(pedidoItem.getValorUnitario());
            return pedidoItemRepository.save(record);
        }).get();


    }

    public List<PedidoItem> carregar(){
        List<PedidoItem> pedidoItemResult = pedidoItemRepository.findAll();
        return pedidoItemResult;
    }

    public void excluir(Long id) throws RegistroNaoEncontrado {
        validarSeRegistroExiste(pedidoItemRepository, id);

        pedidoItemRepository.deleteById(id);
    }




}
