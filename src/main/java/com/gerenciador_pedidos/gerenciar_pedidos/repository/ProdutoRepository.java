package com.gerenciador_pedidos.gerenciar_pedidos.repository;

import com.gerenciador_pedidos.gerenciar_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
