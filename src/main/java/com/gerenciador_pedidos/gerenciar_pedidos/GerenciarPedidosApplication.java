package com.gerenciador_pedidos.gerenciar_pedidos;

import com.gerenciador_pedidos.gerenciar_pedidos.Principal.Principal;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.CategoriaRepository;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.PedidoRepository;
import com.gerenciador_pedidos.gerenciar_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GerenciarPedidosApplication implements CommandLineRunner {
	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(GerenciarPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.exibeMenu();
	}

}
