package br.com.senac.cadastro_clientes_api.repository;

import br.com.senac.cadastro_clientes_api.entities.Clientes;
import br.com.senac.cadastro_clientes_api.entities.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecosRepository extends JpaRepository<Enderecos, Long> {
    List<Enderecos> findByClienteId(Long id);


    long countByCliente(Clientes cliente);
}
