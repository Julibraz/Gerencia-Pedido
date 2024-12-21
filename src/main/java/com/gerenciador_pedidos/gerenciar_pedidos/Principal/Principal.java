package com.gerenciador_pedidos.gerenciar_pedidos.Principal;


import com.gerenciador_pedidos.gerenciar_pedidos.model.Categoria;
import com.gerenciador_pedidos.gerenciar_pedidos.model.Fornecedor;
import com.gerenciador_pedidos.gerenciar_pedidos.model.Pedido;
import com.gerenciador_pedidos.gerenciar_pedidos.model.Produto;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.CategoriaRepository;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.FornecedorRepository;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.PedidoRepository;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class Principal {
    private Scanner scan = new Scanner(System.in);
    private final ProdutoRepository repositorioProd;
    private final PedidoRepository repositorioPed;
    private final CategoriaRepository repositorioCat;
    private final FornecedorRepository repositorioFor;


    @Autowired
    public Principal(ProdutoRepository repositorioProd, PedidoRepository repositorioPed, CategoriaRepository repositorioCat, FornecedorRepository repositorioFor) {
        this.repositorioProd = repositorioProd;
        this.repositorioPed = repositorioPed;
        this.repositorioCat = repositorioCat;
        this.repositorioFor = repositorioFor;
    }


    public void exibeMenu(){

        int opcao =-1;
        while(opcao != 0) {
            var menu = """
                    1 - Inserir produto
                    2 - Inserir categoria
                    3 - Inserir pedido
                    4 - Inserir fornecedor
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
                case 4:
                    insereFornecedor();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

//continuar daqui
public void insereProduto() {
    try {
        System.out.print("Informe o nome do produto: ");
        String nome = scan.nextLine();

        System.out.print("Informe o preço: ");
        Double preco = scan.nextDouble();

        System.out.print("Informe o ID da categoria: ");
        Long idCategoria = scan.nextLong();

        //limpa o buffer
        scan.nextLine();

        //faz a busca da categoria para verificar se ela existe
        Optional<Categoria> categoriaOptional = repositorioCat.findById(idCategoria);
        if (categoriaOptional.isEmpty()) {
            System.out.println("Categoria inexistente.\n");
            return;
        }
        Categoria categoria = categoriaOptional.get();

        //recebe o ID do fornecedor e verifica se ele existe
        System.out.print("Informe o ID do fornecedor: ");
        Long idFornecedor = scan.nextLong();

        Optional<Fornecedor> fornecedorOptional = repositorioFor.findById(idFornecedor);
        if (fornecedorOptional.isEmpty()) {
            System.out.println("Fornecedor inexistente.\n");
            return;
        }
        Fornecedor fornecedor = fornecedorOptional.get();


        //cria e salva o produto
        Produto produto = new Produto(nome, preco, categoria, fornecedor);
        repositorioProd.save(produto);

        System.out.println("Produto inserido com sucesso!\n");
    } catch (Exception e) {
        System.out.println("Ocorreu um erro: " + e.getMessage());
    }
}


    public void insereCategoria(){
        System.out.print("Informe o nome da categoria: ");
        String nome = scan.nextLine();
        Categoria categoria = new Categoria(nome);
        repositorioCat.save(categoria);
    }

    public void inserePedido(){
        LocalDate data = LocalDate.now();

        System.out.println("Informe a lista de IDs dos produtos (separados por espaço): ");
        String inputProdutos = scan.nextLine();
        List<Long> produtoIds = Arrays.stream(inputProdutos.split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        //busca os produtos pelo ID
        List<Produto> produtos = repositorioProd.findAllById(produtoIds);

        if(produtos.isEmpty()){
            System.out.println("Produtos não foram encontrados.");
            return;
        }

        Pedido pedido = new Pedido(data);
        pedido.setProdutos(produtos); //associa os produtos ao pedido


        //atualizar a lista de pedidos nos produtos (relacionamento bidirecional)
        for (Produto produto : produtos) {
            produto.getPedidos().add(pedido);  //Adiciona o pedido ao produto
        }

        //salva o pedido
        repositorioPed.save(pedido);
        //salva os produtos
        repositorioProd.saveAll(produtos);

        System.out.println("Pedido criado com sucesso!\n");
    }


    public void insereFornecedor(){
        System.out.print("Informe o nome do fornecedor: ");
        String nome = scan.nextLine();
        Fornecedor fornecedor = new Fornecedor(nome);
        repositorioFor.save(fornecedor);
    }
}
