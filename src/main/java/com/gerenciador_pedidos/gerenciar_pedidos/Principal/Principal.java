package com.gerenciador_pedidos.gerenciar_pedidos.Principal;


import com.gerenciador_pedidos.gerenciar_pedidos.model.Categoria;
import com.gerenciador_pedidos.gerenciar_pedidos.model.Pedido;
import com.gerenciador_pedidos.gerenciar_pedidos.model.Produto;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.CategoriaRepository;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.PedidoRepository;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Scanner;

@Service
public class Principal {
    private Scanner scan = new Scanner(System.in);
    private final ProdutoRepository repositorioProd;
    private final PedidoRepository repositorioPed;
    private final CategoriaRepository repositorioCat;


    @Autowired
    public Principal(ProdutoRepository repositorioProd, PedidoRepository repositorioPed, CategoriaRepository repositorioCat) {
        this.repositorioProd = repositorioProd;
        this.repositorioPed = repositorioPed;
        this.repositorioCat = repositorioCat;
    }


    public void exibeMenu(){

        int opcao =-1;
        while(opcao != 0) {
            var menu = """
                    1 - Inserir produto
                    2 - Inserir categoria
                    3 - Inserir pedido
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    insereProduto();
                    break;
                case 2:
                    insereCategoria();
                    break;
                case 3:
                    inserePedido();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    public void insereProduto(){
        System.out.print("Informe o nome do produto: ");
        String nome = scan.nextLine();
        System.out.print("Informe o preço: ");
        Double preco = scan.nextDouble();
        Produto produto = new Produto(nome, preco);
        repositorioProd.save(produto);
    }

    public void insereCategoria(){
        System.out.print("Informe o nome da categoria: ");
        String nome = scan.nextLine();
        Categoria categoria = new Categoria(nome);
        repositorioCat.save(categoria);
    }

    public void inserePedido(){
        System.out.print("Informe um id para o pedido: ");
        Long id = scan.nextLong();
        LocalDate data = LocalDate.now();
        Pedido pedido = new Pedido(id, data);
        repositorioPed.save(pedido);
    }
}
