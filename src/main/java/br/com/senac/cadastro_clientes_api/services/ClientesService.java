package br.com.senac.cadastro_clientes_api.services;

import br.com.senac.cadastro_clientes_api.controllers.dtos.ClientesRequest;
import br.com.senac.cadastro_clientes_api.entities.Clientes;
import br.com.senac.cadastro_clientes_api.entities.Enderecos;
import br.com.senac.cadastro_clientes_api.exceptions.NumeroMaximoDeCadastros;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientesService {
    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private EnderecosService enderecosService;

    public List<Clientes> carregarClientes(){
        List<Clientes> clientesResult = clientesRepository.findAll();
        return clientesResult;
    }


    public Clientes criarCliente(Clientes cliente) throws RegistroNaoEncontrado {
        // valida se o email não está nulo
        if(Objects.isNull(cliente.getEmail())) {
            throw new RegistroNaoEncontrado("Email não informado!");
        }

        // varifica se já existe algum cliente com o email já cadastrado na base
        Optional<Clientes> clienteResult = clientesRepository.findByEmail(cliente.getEmail());
        if(clienteResult.isPresent()) {
            throw new RuntimeException("Já exite cliente cadastrado no banco com o email informado!");
        }

        // zera id do cliente para garantir que o banco gere o ID
        cliente.setId(null);

        return clientesRepository.save(cliente);
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


//metodo que recebe o dto criado antes

    public Clientes criarClienteCompleto(ClientesRequest cliente) throws RegistroNaoEncontrado, NumeroMaximoDeCadastros {
//transforma o dto em enidade. passando os parametros de dto como parametro
    Clientes clientePersist = new Clientes();
    clientePersist.setNome(cliente.getNome());
        clientePersist.setSobrenome(cliente.getSobrenome());
        clientePersist.setDataNascimento(cliente.getDataNascimento());
        clientePersist.setEmail(cliente.getEmail());
        clientePersist.setIdade(cliente.getIdade());
        clientePersist.setDdd(cliente.getDdd());
        clientePersist.setTelefone(cliente.getTelefone());
        clientePersist.setSexo(cliente.getSexo());
        clientePersist.setDocumento(cliente.getDocumento());

//this para chamar o metodo e criar cliente que ja existe e nao precisar passa rpor validaçao
    Clientes clienteResult = this.criarCliente(clientePersist);

    if(cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty()){

        List<Enderecos> enderecoResult = enderecosService.criarEnderecos(clienteResult, cliente.getEnderecos());
        clienteResult.setEndereco(enderecoResult);
    }
        return clienteResult;
    }



}
