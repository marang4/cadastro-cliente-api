package br.com.senac.cadastro_clientes_api.controllers;

import br.com.senac.cadastro_clientes_api.entities.PedidoItem;
import br.com.senac.cadastro_clientes_api.exceptions.NaoFoiPossivelAlterar;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.services.PedidoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidoItem")
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;


    @GetMapping("/listar")
    public ResponseEntity<?> listarPedidoItem(){
        try{
            return ResponseEntity.ok(pedidoItemService.carregar());
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }



    @PostMapping("/criar")
   ResponseEntity<?> criarPedidoItem(@RequestBody PedidoItem pedidoItem){
       try {
            PedidoItem pedidoResult = pedidoItemService.criarPedidoItem(pedidoItem);
            return ResponseEntity.ok(pedidoResult);
        }catch (RegistroNaoEncontrado e) {
           e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPedidoItem(@RequestBody PedidoItem pedidoItem, @PathVariable Long id) throws RegistroNaoEncontrado {
        try {
            PedidoItem pedidoItemResult = pedidoItemService.atualizarPedidoItem(id, pedidoItem);
            return ResponseEntity.ok(pedidoItemResult);
        }catch (RegistroNaoEncontrado e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }catch (NaoFoiPossivelAlterar e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            pedidoItemService.excluir(id);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
