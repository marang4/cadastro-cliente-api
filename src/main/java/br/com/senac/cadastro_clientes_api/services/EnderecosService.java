package br.com.senac.cadastro_clientes_api.services;


import br.com.senac.cadastro_clientes_api.controllers.dtos.EnderecosRequest;
import br.com.senac.cadastro_clientes_api.entities.Clientes;
import br.com.senac.cadastro_clientes_api.entities.Enderecos;
import br.com.senac.cadastro_clientes_api.exceptions.NumeroMaximoDeCadastros;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.repository.ClientesRepository;
import br.com.senac.cadastro_clientes_api.repository.EnderecosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecosService {

    @Autowired
    private EnderecosRepository enderecosRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    //listar todos os enderecos cadastrados na base de dado("/listar")

    public List<Enderecos> carregarEnderecos() {

        List<Enderecos> listaEnderecos = enderecosRepository.findAll();

        return listaEnderecos;
    }

    public List<Enderecos> listarEnderecosPorCliente(Long clienteId) throws RegistroNaoEncontrado {
        // Buscar os endereços com base no ID do cliente
        List<Enderecos> enderecos = enderecosRepository.findByClienteId(clienteId);
        if (enderecos.isEmpty()) {
            throw new RegistroNaoEncontrado("Nenhum endereço encontrado para este cliente.");
        }
        return enderecos;
    }




        public Enderecos criarEndereco(Enderecos endereco) throws NumeroMaximoDeCadastros {

        if (endereco.getCliente() == null || endereco.getCliente().getId() == null) {
            throw new RuntimeException("ID do cliente é obrigatório!");
        }


        Long idCliente = endereco.getCliente().getId();
        Clientes cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));


        long quantidadeEnderecos = enderecosRepository.countByCliente(cliente);
        if (quantidadeEnderecos >= 3) {
            throw new NumeroMaximoDeCadastros("O cliente já possui o limite de 3 endereços cadastrados.");
        }


        endereco.setCliente(cliente);


        return enderecosRepository.save(endereco);

    }



        public Enderecos atualizarEndereco(Long id, Enderecos endereco) throws RegistroNaoEncontrado {

        if (!enderecosRepository.existsById(id)) {
            throw new RegistroNaoEncontrado("Endereço nao encontrado");
        }

        endereco.setId(id);
        Enderecos enderecoResult = enderecosRepository.save(endereco);
       return enderecoResult;



    }


    public void excluirEndereco(Long id) throws RegistroNaoEncontrado {
        if(!enderecosRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Endereço nao encontrado");
        }




        enderecosRepository.deleteById(id);
    }


    //recebe um endereço e uma lsita de clientes
    public List<Enderecos> criarEnderecos(Clientes cliente, List<EnderecosRequest> enderecos ) throws RegistroNaoEncontrado, NumeroMaximoDeCadastros {


        //criar um array de endereços para retornar
        List<Enderecos> saida = new ArrayList<>();


        //percorrer o objeto, convertendo endereço request para endereços para gravar no bd
        for (EnderecosRequest endereco : enderecos) {
            Enderecos enderecoPersist = new Enderecos();

            enderecoPersist.setBairro(endereco.getBairro());
            enderecoPersist.setCep(endereco.getCep());
            enderecoPersist.setCidade(endereco.getCidade());
            enderecoPersist.setLogradouro(endereco.getLogradouro());
            enderecoPersist.setEstado(endereco.getEstado());
            enderecoPersist.setNumero(endereco.getNumero());
            enderecoPersist.setCliente(cliente);

            //joga na lista para serem gravados no bacno
            saida.add(this.criarEndereco(enderecoPersist));

        }

        return saida;

    }



}
