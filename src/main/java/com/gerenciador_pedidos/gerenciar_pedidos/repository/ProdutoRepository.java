package com.gerenciador_pedidos.gerenciar_pedidos.repository;

import com.gerenciador_pedidos.gerenciar_pedidos.model.Pedido;
import com.gerenciador_pedidos.gerenciar_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
//    @Query("SELECT p FROM Produto p WHERE p.nome = :nome") //retorna os produtos pelo nome
//    List<Produto> BuscaPorNome(String nome);
//
//    @Query("SELECT p FROM Produto p WHERE p.categoria.nome = :categoriaNome") //retorna os produtos de uma categoria
//    List<Produto> BuscaPorCategoriaNome(String categoriaNome);

    @Query("SELECT p FROM Produto p WHERE p.preco >= :preco") //retorna os produtos com preço maior ou igual a um valor
    List<Produto> BuscaPrecoMaior(Double preco);

    @Query("SELECT p FROM Produto p WHERE p.preco <= :preco") //retorna os produtos com preço menor ou igual a um valor
    List<Produto> buscaPrecoMenor(Double preco);

//    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:termo%") //
//    List<Produto> BuscaPorContenhaTermo(String termo);


    @Query("SELECT p FROM Produto p WHERE p.categoria.nome = :categoriaNome ORDER BY p.preco ASC") //ORDENAR PRODUTOS PELO NOME EM ORDEM CRESCENTE PELO PREÇO
    List<Produto> BuscaCategoriaNomeOrderByPrecoAsc(String categoriaNome);

    @Query("SELECT p FROM Produto p WHERE p.categoria.nome = :categoriaNome ORDER BY p.preco DESC")
    List<Produto> findByCategoriaNomeOrderByPrecoDesc(String categoriaNome);

//    //@Query("SELECT COUNT(p) FROM Produto p WHERE p.categoria.nome = :categoriaNome") //conta os produtos de uma categoria
//    long countByCategoriaNome(String categoriaNome);

    @Query("SELECT COUNT(p) FROM Produto p WHERE p.preco > :preco") //conta os produtos com preço maior que um valor
    long countByPrecoGreaterThan(Double preco);

    @Query("SELECT p FROM Produto p WHERE p.preco <= :preco AND p.nome LIKE %:termo%") //retorna os produtos com preço menor ou igual a um valor e que contenham um termo
    List<Produto> findByPrecoLessThanOrNomeContaining(Double preco, String termo);

    @Query("SELECT p FROM Pedido p WHERE p.data > :data") //retorna os pedidos depois de uma data
    List<Pedido> BuscaPedidoPorDataApos(LocalDate data);

    @Query("SELECT p FROM Pedido p WHERE p.data < :data") //retorna os pedidos antes de uma data
    List<Pedido> BuscaPedidoPorDataAntes(LocalDate data);

    @Query("SELECT p FROM Pedido p WHERE p.data BETWEEN :dataInicio AND :dataFim") //retorna os pedidos entre duas datas
    List<Pedido> BuscaPedidoPorDataEntre(LocalDate dataInicio, LocalDate dataFim);

    @Query("SELECT p FROM Produto p ORDER BY p.preco DESC LIMIT 3") //retorna os 3 produtos mais caros
    List<Produto> BuscaTop3ByPrecoDesc();

//    @Query("SELECT p FROM Produto p WHERE p.categoria.nome = :categoriaNome ORDER BY p.preco ASC LIMIT 5") //retorna os 5 produtos mais caros de uma categoria
//    List<Produto> BuscaTop5ByCategoriaNomeOrderByPrecoAsc(String categoriaNome);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoriaNomeIgnoreCase(String categoriaNome);

    long countByCategoriaNomeIgnoreCase(String categoriaNome);

    List<Produto> findTop5ByCategoriaNomeIgnoreCaseOrderByPrecoAsc(String categoriaNome);
}
