package br.com.senac.cadastro_clientes_api.repository;

import br.com.senac.cadastro_clientes_api.entities.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

}
