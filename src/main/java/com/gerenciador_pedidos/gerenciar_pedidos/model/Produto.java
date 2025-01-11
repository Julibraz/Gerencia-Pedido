package com.gerenciador_pedidos.gerenciar_pedidos.model;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "valor")
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id") //cria a coluna que define a chave estrangeira
    private Categoria categoria;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "produto_pedido", //cria a tabela de relacionamento(intermediaria)
            joinColumns = @JoinColumn(name = "produto_id"), //coluna que refere a chave primaria de produto
            inverseJoinColumns = @JoinColumn(name = "pedido_id")) //coluna que refere a chave primaria de pedido
    private List<Pedido> pedidos;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id") //cria a coluna que define a chave estrangeira
    private Fornecedor fornecedor;

    public Produto() {}

    public Produto(String nome, Double preco, Categoria categoria, Fornecedor fornecedor) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }


}
