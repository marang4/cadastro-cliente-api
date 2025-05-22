package br.com.senac.cadastro_clientes_api.controllers.dtos;
import br.com.senac.cadastro_clientes_api.entities.Clientes;
import br.com.senac.cadastro_clientes_api.entities.Enderecos;
import br.com.senac.cadastro_clientes_api.entities.PedidoItem;
import ch.qos.logback.core.net.server.Client;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequest {

    private LocalDateTime dataCriacao;
    private double valorTotal;

    private Clientes clientes;

    private Enderecos endereco;

    private List<PedidoItem> itens;

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Enderecos getEndereco() {
        return endereco;
    }

    public void setEndereco(Enderecos endereco) {
        this.endereco = endereco;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }


    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }
}
