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
                    2 - Criar categoria
                    3 - Criar pedido
                    4 - Inserir fornecedor
                    5 - Buscar produto por nome
                    6 - Exibe todos os produtos de uma categoria
                    7 - Busca produtos apartir de um valor
                    8 - Busca produtos menores que um valor
                    9 - Informa quantos produtos tem em uma categoria
                    10 - Busca o top 3 produtos mais caros
                    11 - Busca os 5 produtos mais caros de uma categoria
                    12 - Busca Pedidos apos uma data
                    13 - Busca Pedidos antes de uma data
                    14 - Busca Pedidos entre duas datas
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
                case 5:
                    buscaPorNome();
                    break;
                case 6:
                    buscaPorCategoriaNome();
                    break;
                case 7:
                    buscaPrecoMaior();
                    break;
                case 8:
                    buscaPrecoMenor();
                    break;
                case 9:
                    countByCategoriaNome();
                    break;
                case 10:
                    buscaTop3MaisCaros();
                    break;
                case 11:
                    findTop5ByCategoriaNomeIgnoreCaseOrderByPrecoAsc();
                    break;
                case 12:
                    buscaPedidoPorDataApos();
                    break;
                case 13:
                    buscaPedidoPorDataAntes();
                    break;
                case 14:
                    buscaPedidoPorDataEntre();
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


    public void buscaPorNome() {
        System.out.print("Informe o nome do produto: ");
        String nome = scan.nextLine().toLowerCase(); // Converter para minúsculo para a busca
        List<Produto> produtos = repositorioProd.findByNomeContainingIgnoreCase(nome);

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com o nome informado.");
        } else {
            produtos.forEach(p -> System.out.printf("Id: %d, Nome: %s, Preço: %.2f, Categoria: %s%n", p.getId(), p.getNome(), p.getPreco(), p.getCategoria().getNome()));
            System.out.println();
        }
    }


    public void buscaPorCategoriaNome() {
        System.out.print("Informe o nome da categoria: ");
        String categoriaNome = scan.nextLine().toLowerCase();
        List<Produto> produtos = repositorioProd.findByCategoriaNomeIgnoreCase(categoriaNome); //IGNORA O CASE SENSITIVE(OU SEJA MAIUSCULO OU MINUSCULO)

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado na categoria informada.");
        } else {
            produtos.forEach(p -> System.out.printf("Categoria: %s, Id Produto: %d, Nome: %s\n", p.getCategoria().getNome(), p.getId(), p.getNome()));
        }
        System.out.println();
    }

    public void buscaPrecoMaior() {
        System.out.print("Informe a partir de que preço deseja pesquisar: ");
        Double preco = scan.nextDouble();
        scan.nextLine();

        List<Produto> produtos = repositorioProd.BuscaPrecoMaior(preco);
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com preço maior que o valor informado.");
        } else {
            produtos.forEach(p -> System.out.printf("Id: %d, Nome: %s, Preço: %.2f, Categoria: %s%n", p.getId(), p.getNome(), p.getPreco(), p.getCategoria().getNome()));
        }
        System.out.println();
    }

    public void buscaPrecoMenor() {
        System.out.print("Informe o valor máximo do preço: ");
        Double preco = scan.nextDouble();
        scan.nextLine();

        List<Produto> produtos = repositorioProd.buscaPrecoMenor(preco);
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com preço menor que o valor informado.");
        } else {
            produtos.forEach(p -> System.out.printf("Id: %d, Nome: %s, Preço: %.2f, Categoria: %s%n", p.getId(), p.getNome(), p.getPreco(), p.getCategoria().getNome()));
        }
        System.out.println();
    }

    public void countByCategoriaNome() {
        System.out.print("Informe o nome da categoria: ");
        String categoriaNome = scan.nextLine().toUpperCase();

        long count = repositorioProd.countByCategoriaNomeIgnoreCase(categoriaNome);
        System.out.println("Número de produtos na categoria '" + categoriaNome + "': " + count);
    }

    public void buscaTop3MaisCaros() {
        List<Produto> produtos = repositorioProd.BuscaTop3ByPrecoDesc();
        System.out.println("Os 3 produtos mais caros são:");
        produtos.forEach(p -> System.out.printf("Id: %d, Nome: %s, Preço: %.2f, Categoria: %s%n", p.getId(), p.getNome(), p.getPreco(), p.getCategoria().getNome()));
        System.out.println();
    }

    public void findTop5ByCategoriaNomeIgnoreCaseOrderByPrecoAsc() {
        System.out.print("Informe o nome da categoria: ");
        String categoriaNome = scan.nextLine().toUpperCase();

        List<Produto> produtos = repositorioProd.findTop5ByCategoriaNomeIgnoreCaseOrderByPrecoAsc(categoriaNome);

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado na categoria informada.");
        } else {
            produtos.forEach(p -> System.out.printf("Id: %d, Nome: %s, Preço: %.2f, Categoria: %s%n", p.getId(), p.getNome(), p.getPreco(), p.getCategoria().getNome()));
        }
        System.out.println();
    }

    public void buscaPedidoPorDataApos() {
        System.out.print("Informe a data inicial (yyyy-MM-dd): ");
        LocalDate data = LocalDate.parse(scan.nextLine());

        List<Pedido> pedidos = repositorioProd.BuscaPedidoPorDataApos(data);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado após a data informada.");
        } else {
            pedidos.forEach(p -> System.out.printf("Id: %d, Data: %s%n", p.getId(), p.getData()));
        }
        System.out.println();
    }

    public void buscaPedidoPorDataAntes() {
        System.out.print("Informe a data final (yyyy-MM-dd): ");
        LocalDate data = LocalDate.parse(scan.nextLine());

        List<Pedido> pedidos = repositorioProd.BuscaPedidoPorDataAntes(data);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado antes da data informada.");
        } else {
            pedidos.forEach(p -> System.out.printf("Id: %d, Data: %s%n", p.getId(), p.getData()));
        }
        System.out.println();
    }

    public void buscaPedidoPorDataEntre() {
        System.out.print("Informe a data inicial (yyyy-MM-dd): ");
        LocalDate dataInicio = LocalDate.parse(scan.nextLine());
        System.out.print("Informe a data final (yyyy-MM-dd): ");
        LocalDate dataFim = LocalDate.parse(scan.nextLine());

        List<Pedido> pedidos = repositorioProd.BuscaPedidoPorDataEntre(dataInicio, dataFim);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado entre as datas informadas.");
        } else {
            pedidos.forEach(p -> System.out.printf("Id: %d, Data: %s%n", p.getId(), p.getData()));
        }
        System.out.println();
    }



}
