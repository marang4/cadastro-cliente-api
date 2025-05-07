package br.com.senac.cadastro_clientes_api.services;

import br.com.senac.cadastro_clientes_api.entities.Pedido;
import br.com.senac.cadastro_clientes_api.entities.Produto;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.repository.PedidoRepository;
import br.com.senac.cadastro_clientes_api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produto) throws RegistroNaoEncontrado {
        if(!produtoRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Produto não encontrado");
        }
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() throws RegistroNaoEncontrado {
        if ( produtoRepository.findAll().isEmpty()){
            throw new RegistroNaoEncontrado("Sem produtos cadastrados");
        }
        return produtoRepository.findAll();
    }



    public void excluirProduto(Long id) throws RegistroNaoEncontrado {
        if(!produtoRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }


}
