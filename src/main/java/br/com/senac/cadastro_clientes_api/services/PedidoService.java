package br.com.senac.cadastro_clientes_api.services;

import br.com.senac.cadastro_clientes_api.controllers.dtos.ItemPedidoRequest;
import br.com.senac.cadastro_clientes_api.controllers.dtos.PedidoItensRequest;
import br.com.senac.cadastro_clientes_api.controllers.dtos.PedidoRequest;
import br.com.senac.cadastro_clientes_api.entities.*;
import br.com.senac.cadastro_clientes_api.exceptions.PertenceAOutroCliente;
import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import br.com.senac.cadastro_clientes_api.repository.ClientesRepository;
import br.com.senac.cadastro_clientes_api.repository.EnderecosRepository;
import br.com.senac.cadastro_clientes_api.repository.PedidoItemRepository;
import br.com.senac.cadastro_clientes_api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.senac.cadastro_clientes_api.utils.ValidacoesUtil.validarSeRegistroExiste;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EnderecosRepository enderecosRepository;
    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    public Pedido cadastrarPedido(Pedido pedido) throws RegistroNaoEncontrado {

//        Long clienteId = pedido.getCliente().getId();
//        Long enderecoId = pedido.getEndereco().getId();
//
//        Clientes cliente = clientesRepository.findById(clienteId)
//                .orElseThrow(() -> new RegistroNaoEncontrado("Cliente nao encontrado"));
//
//        Enderecos endereco = enderecosRepository.findById(enderecoId)
//                .orElseThrow(() -> new RegistroNaoEncontrado("Endere;co noa encontrado"));
//
//
//        if (!endereco.getCliente().getId().equals(cliente.getId())) {
//            throw  new RegistroNaoEncontrado("o endereço pertence a outro cliente");
//        }
//
//
//        return pedidoRepository.save(pedido);

        if (pedido.getCliente() == null){
            throw new RegistroNaoEncontrado("Cliente nao informado");
        }

        Optional<Enderecos> resultEndereco = enderecosRepository.findById(pedido.getEndereco().getId());
        if (resultEndereco.isEmpty()) {
            throw new RegistroNaoEncontrado("Endereço noa existe");
        }

        Clientes clienteResult = resultEndereco.get().getCliente();

        if(clienteResult.getId() != pedido.getCliente().getId()){
            throw new RegistroNaoEncontrado("Cliente nao existe");
        }

        pedido.setId(null);

        return pedidoRepository.save(pedido);

    }


    public Pedido atualizarPedido(Long id, Pedido pedido) throws RegistroNaoEncontrado, PertenceAOutroCliente {
//        Pedido pedidoResult = pedidoRepository.findById(id)
//                .orElseThrow(() -> new RegistroNaoEncontrado("Pedido nao encontrado"));
//
//        Long enderecoId = pedido.getEndereco().getId();
//        Long clienteId = pedido.getCliente().getId();
//
//        Clientes cliente = clientesRepository.findById(clienteId)
//                .orElseThrow(() -> new RegistroNaoEncontrado("Cliente nao encontrado"));
//
//
//
//
//        if (pedidoResult.getCliente() == null) {
//            pedidoResult.setCliente(cliente);
//        } else if (!pedidoResult.getCliente().getId().equals(clienteId)) {
//            throw new RegistroNaoEncontrado("nao é possivel alterar o cliente");
//        }
//
//
//
//        Enderecos novoEndereco = enderecosRepository.findById(enderecoId)
//                .orElseThrow(() -> new RegistroNaoEncontrado("Endereço nao encontrao"));
//
//        if (!novoEndereco.getCliente().getId().equals(clienteId)) {
//            throw new PertenceAOutroCliente("Endereço nao pertence ao cliente informado");
//        }
//
//        pedidoResult.setId(id);
//        pedidoResult.setEndereco(novoEndereco);
//
//        return pedidoRepository.save(pedidoResult);


        Optional<Pedido> pedidoResult = pedidoRepository.findById(id);
        if (pedidoResult.isEmpty()) {
            throw new RegistroNaoEncontrado("Pedido nao encontrado");
        }

        Optional<Enderecos> enderecoResult = enderecosRepository.findById(pedido.getEndereco().getId());
        if(enderecoResult.isEmpty()) {
            throw new RegistroNaoEncontrado("Endereço nao existe");
        }

        if(pedidoResult.get().getCliente().getId() != enderecoResult.get().getCliente().getId()){
            throw new RegistroNaoEncontrado("Endereço não eiste");
        }

        return pedidoRepository.findById(id).map(record -> {
            record.setEndereco(pedido.getEndereco());
            record.setValorTotal(pedido.getValorTotal());

            return pedidoRepository.save(record);
        }).get();


    }

    public List<Pedido> carregar() {
        List<Pedido> pedidoResult = pedidoRepository.findAll();
        return pedidoResult;
    }

    public void excluir(Long id) throws RegistroNaoEncontrado {
        validarSeRegistroExiste(pedidoRepository, id);

        pedidoRepository.deleteById(id);
    }


    public Pedido criarPedidoCompleto(PedidoItensRequest pedidos) throws RegistroNaoEncontrado, RegistroNaoEncontrado, RegistroNaoEncontrado {

        Pedido pedidoPersist = new Pedido();


        Clientes cliente = new Clientes();
        cliente.setId(pedidos.getClienteId());


        Enderecos enderecos = new Enderecos();
        enderecos.setId(pedidos.getEnderecoId());


        pedidoPersist.setDataCriacao(pedidos.getDataCriacao().atStartOfDay());
        pedidoPersist.setValorTotal(pedidos.getValorTotal());
        pedidoPersist.setCliente(cliente);
        pedidoPersist.setEndereco(enderecos);


        Pedido pedidoResult = this.cadastrarPedido(pedidoPersist);


        for (ItemPedidoRequest itemDto : pedidos.getPedidoItens()){

            PedidoItem item = new PedidoItem();
            item.setPedido(pedidoResult);


            Produto produto = new Produto();
            produto.setId(itemDto.getProdutoId());
            item.setProduto(produto);


            item.setQuantidade(itemDto.getQuantidade());
            item.setValorUnitario(itemDto.getValorUnitario());


            pedidoItemRepository.save(item);
        }


        return pedidoResult;
    }


}
