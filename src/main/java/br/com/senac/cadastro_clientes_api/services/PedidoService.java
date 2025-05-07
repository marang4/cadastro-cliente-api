package br.com.senac.cadastro_clientes_api.services;

import br.com.senac.cadastro_clientes_api.entities.Clientes;
import br.com.senac.cadastro_clientes_api.entities.Enderecos;
import br.com.senac.cadastro_clientes_api.entities.Pedido;
import br.com.senac.cadastro_clientes_api.exceptions.PertenceAOutroCliente;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.repository.ClientesRepository;
import br.com.senac.cadastro_clientes_api.repository.EnderecosRepository;
import br.com.senac.cadastro_clientes_api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EnderecosRepository enderecosRepository;
    @Autowired
    private ClientesRepository clientesRepository;

    public Pedido cadastrarPedido(Pedido pedido) throws RegistroNaoEncontrado {

        Long clienteId = pedido.getCliente().getId();
        Long enderecoId = pedido.getEndereco().getId();

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new RegistroNaoEncontrado("Cliente nao encontrado"));

        Enderecos endereco = enderecosRepository.findById(enderecoId)
                .orElseThrow(() -> new RegistroNaoEncontrado("Endere;co noa encontrado"));


        if (!endereco.getCliente().getId().equals(cliente.getId())) {
            throw  new RegistroNaoEncontrado("o endereço pertence a outro cliente");
        }


        return pedidoRepository.save(pedido);

    }


    public Pedido atualizarPedido(Long id, Pedido pedido) throws RegistroNaoEncontrado, PertenceAOutroCliente {
        Pedido pedidoResult = pedidoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontrado("Pedido nao encontrado"));

        Long enderecoId = pedido.getEndereco().getId();
        Long clienteId = pedido.getCliente().getId();

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new RegistroNaoEncontrado("Cliente nao encontrado"));




        if (pedidoResult.getCliente() == null) {
            pedidoResult.setCliente(cliente);
        } else if (!pedidoResult.getCliente().getId().equals(clienteId)) {
            throw new RegistroNaoEncontrado("nao é possivel alterar o cliente");
        }



        Enderecos novoEndereco = enderecosRepository.findById(enderecoId)
                .orElseThrow(() -> new RegistroNaoEncontrado("Endereço nao encontrao"));

        if (!novoEndereco.getCliente().getId().equals(clienteId)) {
            throw new PertenceAOutroCliente("Endereço nao pertence ao cliente informado");
        }

        pedidoResult.setId(id);
        pedidoResult.setEndereco(novoEndereco);

        return pedidoRepository.save(pedidoResult);
    }




}
