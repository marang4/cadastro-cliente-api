package br.com.senac.cadastro_clientes_api.controllers;

import br.com.senac.cadastro_clientes_api.controllers.dtos.ClientesRequest;
import br.com.senac.cadastro_clientes_api.entities.Clientes;
import br.com.senac.cadastro_clientes_api.services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @GetMapping("/listar")
    public ResponseEntity<List<Clientes>> carregarClientes(){
        List<Clientes>  clientes = clientesService.carregarClientes();
        return ResponseEntity.ok(clientes);

    }

    @PostMapping("/criar")
    public ResponseEntity<Clientes> criarCliente(@RequestBody Clientes cliente){

        try {
            return ResponseEntity.ok(clientesService.criarCliente(cliente));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Clientes> atualizarCliente(@PathVariable Long id, @RequestBody Clientes cliente){
       try {
           return ResponseEntity.ok(clientesService.atualizarCliente(id, cliente));
       }catch (RuntimeException e) {
           return ResponseEntity.status(204).body(null);
       }catch (Exception e ) {
           return ResponseEntity.badRequest().body(null);
       }


    }



    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirCliente(@PathVariable Long id){
        try {
            clientesService.excluirCliente(id);
            return ResponseEntity.ok(null);
        }catch (RuntimeException e) {
            return ResponseEntity.status(204).body(null);
        }catch (Exception e ) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/completo/criar")
    public ResponseEntity<?> criarClienteCompleto(@RequestBody ClientesRequest cliente) {

        try {

            return ResponseEntity.created(null).body(clientesService.criarClienteCompleto(cliente));


        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


}
