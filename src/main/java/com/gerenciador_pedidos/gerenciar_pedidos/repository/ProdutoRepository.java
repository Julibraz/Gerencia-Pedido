package com.gerenciador_pedidos.gerenciar_pedidos.repository;

import com.gerenciador_pedidos.gerenciar_pedidos.model.Pedido;
import com.gerenciador_pedidos.gerenciar_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNome(String nome);

    List<Produto> findByCategoriaNome(String categoriaNome);

    List<Produto> findByPrecoGreaterThan(Double preco);

    List<Produto> findByPrecoLessThan(Double preco);

    List<Produto> findByNomeContaining(String termo);

    List<Pedido> findByDataEntregaIsNull();

    List<Pedido> findByDataEntregaIsNotNull();

    List<Produto> findByCategoriaNomeOrderByPrecoAsc(String categoriaNome);

    List<Produto> findByCategoriaNomeOrderByPrecoDesc(String categoriaNome);

    long countByCategoriaNome(String categoriaNome);

    long countByPrecoGreaterThan(Double preco);

    List<Produto> findByPrecoLessThanOrNomeContaining(Double preco, String termo);

    List<Pedido> findByDataPedidoAfter(LocalDate data);

    List<Pedido> findByDataPedidoBefore(LocalDate data);

    List<Pedido> findByDataPedidoBetween(LocalDate dataInicio, LocalDate dataFim);

    List<Produto> findTop3ByPrecoDesc();

    List<Produto> findTop5ByCategoriaNomeOrderByPrecoAsc(String categoriaNome);

}
