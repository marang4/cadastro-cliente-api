package br.com.senac.cadastro_clientes_api.controllers;

import br.com.senac.cadastro_clientes_api.entities.Produto;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.services.ProdutoService;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @PostMapping("/criar")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto){
        Produto produtoresult = produtoService.criarProduto(produto);
        return ResponseEntity.ok(produtoresult);

    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarProdutos(){
        try{
            List<Produto> produtoResult = produtoService.listarProdutos();
            return ResponseEntity.ok(produtoResult);
        }catch (RegistroNaoEncontrado e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto){
        try{
            Produto produtoResult = produtoService.atualizarProduto(id, produto);
            return ResponseEntity.ok(produtoResult);
        }catch (RegistroNaoEncontrado e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id){
        try{
            produtoService.excluirProduto(id);
            return ResponseEntity.ok(null);
        }catch (RegistroNaoEncontrado e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
