package com.ecommerce.main;

import com.ecommerce.service.ClienteService;
import com.ecommerce.service.PedidoService;
import com.ecommerce.service.ProdutoService;

public class Main {
    public static void main(String[] args) {
        // Instancia os serviços
        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();
        PedidoService pedidoService = new PedidoService(clienteService, produtoService);

        // Instancia e exibe o menu
        Menu menu = new Menu(clienteService, produtoService, pedidoService);
        menu.exibirMenuPrincipal();

        System.out.println("Aplicação encerrada.");
    }
}