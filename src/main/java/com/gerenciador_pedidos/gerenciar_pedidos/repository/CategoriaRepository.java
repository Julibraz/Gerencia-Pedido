package com.gerenciador_pedidos.gerenciar_pedidos.repository;

import com.gerenciador_pedidos.gerenciar_pedidos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
