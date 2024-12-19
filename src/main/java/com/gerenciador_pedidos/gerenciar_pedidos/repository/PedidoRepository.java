package com.gerenciador_pedidos.gerenciar_pedidos.repository;

import com.gerenciador_pedidos.gerenciar_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
