package br.com.senac.cadastro_clientes_api.controllers;

import br.com.senac.cadastro_clientes_api.entities.Enderecos;
import br.com.senac.cadastro_clientes_api.exceptions.NumeroMaximoDeCadastros;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.services.EnderecosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enderecos")
public class EnderecosController {

    @Autowired
    EnderecosService enderecosService;

    @GetMapping("/listar")
    public ResponseEntity<?> carregarEnderecos() {

        List<Enderecos> enderecoResult = enderecosService.carregarEnderecos();

        return ResponseEntity.ok().body(enderecoResult);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Enderecos>> listarEnderecosPorCliente(@PathVariable Long clienteId) throws RegistroNaoEncontrado {
        List<Enderecos> enderecos = enderecosService.listarEnderecosPorCliente(clienteId);
        return ResponseEntity.ok(enderecos);
    }


    @PostMapping("/criar")
    public ResponseEntity<?> criarEndereco(@RequestBody Enderecos endereco) throws NumeroMaximoDeCadastros {
        try {
            Enderecos novoEndereco = enderecosService.criarEndereco(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
        }catch (NumeroMaximoDeCadastros e) {
            e.printStackTrace();
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEnderecos(@PathVariable Long id, @RequestBody Enderecos endereco) throws RegistroNaoEncontrado {
    try {

        return ResponseEntity.ok(enderecosService.atualizarEndereco(id, endereco));
    } catch (RegistroNaoEncontrado e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    }


    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirEnderecos(@PathVariable Long id) throws RegistroNaoEncontrado {

        try {
            enderecosService.excluirEndereco(id);
            return ResponseEntity.ok(null);
        }catch (RegistroNaoEncontrado e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


}
