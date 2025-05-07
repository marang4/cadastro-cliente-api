package br.com.senac.cadastro_clientes_api.controllers;

import br.com.senac.cadastro_clientes_api.entities.Pedido;
import br.com.senac.cadastro_clientes_api.exceptions.PertenceAOutroCliente;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) throws RegistroNaoEncontrado {
        try {
            Pedido novoPedido = pedidoService.cadastrarPedido(pedido);
            return ResponseEntity.ok(novoPedido);
        }catch (RegistroNaoEncontrado e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }


    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPedido(@RequestBody Pedido pedido, @PathVariable Long id) throws RegistroNaoEncontrado {
        try {
            Pedido pedidoResult = pedidoService.atualizarPedido(id, pedido);
            return ResponseEntity.ok(pedidoResult);
        }catch (RegistroNaoEncontrado e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }catch (PertenceAOutroCliente e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


}
