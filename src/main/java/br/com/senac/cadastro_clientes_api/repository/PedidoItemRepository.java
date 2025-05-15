package br.com.senac.cadastro_clientes_api.repository;

import br.com.senac.cadastro_clientes_api.entities.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    Optional<PedidoItem> findByPedidoIdAndProdutoId(Long pedidoId, Long produtoId);
}
