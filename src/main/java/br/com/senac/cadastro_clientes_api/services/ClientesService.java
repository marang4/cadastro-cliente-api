package br.com.senac.cadastro_clientes_api.services;

import br.com.senac.cadastro_clientes_api.entities.Clientes;
import br.com.senac.cadastro_clientes_api.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesService {
    @Autowired
    private ClientesRepository clientesRepository;

    public List<Clientes> carregarClientes(){
        List<Clientes> clientesResult = clientesRepository.findAll();
        return clientesResult;
    }


    public Clientes criarCliente(Clientes cliente){
        Clientes clienteResult = clientesRepository.save(cliente);

        return clienteResult;
    }



    public Clientes atualizarCliente(Long id, Clientes cliente){
        if (!clientesRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        cliente.setId(id);
        Clientes clienteResult = clientesRepository.save(cliente);
        return clienteResult;
    }


    public void excluirCliente(Long id){
        if (!clientesRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }

        clientesRepository.deleteById(id);
    }






}
