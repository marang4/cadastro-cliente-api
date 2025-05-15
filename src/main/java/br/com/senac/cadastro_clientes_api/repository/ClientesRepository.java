package br.com.senac.cadastro_clientes_api.repository;

import br.com.senac.cadastro_clientes_api.entities.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    Optional<Clientes> findByEmail(String email);
}
