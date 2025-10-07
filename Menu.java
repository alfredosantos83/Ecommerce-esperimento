package com.ecommerce.main;

import com.ecommerce.model.Pedido;
import com.ecommerce.service.ClienteService;
import com.ecommerce.service.PedidoService;
import com.ecommerce.service.ProdutoService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;
    private final Scanner scanner;

    public Menu(ClienteService clienteService, ProdutoService produtoService, PedidoService pedidoService) {
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n--- E-COMMERCE ---");
            System.out.println("1. Gestão de Clientes");
            System.out.println("2. Gestão de Produtos");
            System.out.println("3. Gestão de Pedidos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuProdutos();
                    break;
                case 3:
                    menuPedidos();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuClientes() {
        int opcao;
        do {
            System.out.println("\n--- GESTÃO DE CLIENTES ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Pesquisar Cliente por Nome");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    pesquisarClientePorNome();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuProdutos() {
        int opcao;
        do {
            System.out.println("\n--- GESTÃO DE PRODUTOS ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Pesquisar Produto por Nome");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    atualizarProduto();
                    break;
                case 4:
                    pesquisarProdutoPorNome();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuPedidos() {
        int opcao;
        do {
            System.out.println("\n--- GESTÃO DE PEDIDOS ---");
            System.out.println("1. Criar Novo Pedido");
            System.out.println("2. Gerenciar Pedido Existente");
            System.out.println("3. Listar Pedidos de um Cliente (por documento)");
            System.out.println("4. Listar Todos os Pedidos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    gerenciarPedido();
                    break;
                case 3:
                    listarPedidosPorCliente();
                    break;
                case 4:
                    listarTodosOsPedidos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Documento do cliente: ");
        String documento = scanner.nextLine();
        clienteService.cadastrarCliente(nome, documento);
    }

    private void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        clienteService.listarClientes().forEach(System.out::println);
    }

    private void atualizarCliente() {
        System.out.print("Documento do cliente a ser atualizado: ");
        String documento = scanner.nextLine();
        System.out.print("Novo nome do cliente: ");
        String novoNome = scanner.nextLine();
        clienteService.atualizarCliente(documento, novoNome);
    }

    private void pesquisarClientePorNome() {
        System.out.print("Digite o nome ou parte do nome para pesquisar: ");
        String nome = scanner.nextLine();
        clienteService.findByNome(nome).forEach(System.out::println);
    }

    private void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço do produto: ");
        double preco = lerDouble();
        produtoService.cadastrarProduto(nome, preco);
    }

    private void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        produtoService.listarProdutos().forEach(p ->
                System.out.printf("ID: %d, Nome: %s, Preço: R$%.2f%n", p.getId(), p.getNome(), p.getPreco()));
    }

    private void atualizarProduto() {
        System.out.print("ID do produto a ser atualizado: ");
        long id = lerLong();
        System.out.print("Novo nome do produto: ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo preço do produto: ");
        double novoPreco = lerDouble();
        produtoService.atualizarProduto(id, novoNome, novoPreco);
    }

    private void pesquisarProdutoPorNome() {
        System.out.print("Digite o nome ou parte do nome para pesquisar: ");
        String nome = scanner.nextLine();
        produtoService.findByNome(nome).forEach(p ->
                System.out.printf("ID: %d, Nome: %s, Preço: R$%.2f%n", p.getId(), p.getNome(), p.getPreco()));
    }

    private void criarPedido() {
        System.out.print("Documento do cliente para o pedido: ");
        String documento = scanner.nextLine();
        pedidoService.criarPedido(documento);
    }

    private void gerenciarPedido() {
        System.out.print("Digite o ID do pedido a ser gerenciado: ");
        long pedidoId = lerLong();

        pedidoService.findPedidoById(pedidoId).ifPresentOrElse(pedido -> {
            int opcao;
            do {
                System.out.println("\n--- GERENCIANDO PEDIDO #" + pedido.getId() + " ---");
                System.out.println("Cliente: " + pedido.getCliente().getNome() + " | Status: " + pedido.getStatus());
                System.out.println("1. Adicionar Item");
                System.out.println("2. Remover Item");
                System.out.println("3. Alterar Quantidade de Item");
                System.out.println("4. Ver Detalhes do Pedido");
                System.out.println("5. Finalizar Pedido (Aguardar Pagamento)");
                System.out.println("6. Realizar Pagamento");
                System.out.println("7. Enviar para Entrega");
                System.out.println("0. Voltar");
                System.out.print("Escolha uma opção: ");
                opcao = lerInteiro();

                switch (opcao) {
                    case 1: adicionarItemAoPedido(pedidoId); break;
                    case 2: removerItemDoPedido(pedidoId); break;
                    case 3: alterarQuantidadeItem(pedidoId); break;
                    case 4: verDetalhesPedido(pedido); break;
                    case 5: pedidoService.finalizarPedido(pedidoId); break;
                    case 6: pedidoService.pagarPedido(pedidoId); break;
                    case 7: pedidoService.entregarPedido(pedidoId); break;
                }
                // Recarrega o pedido para refletir as mudanças de status
                if (opcao >= 5 && opcao <= 7) {
                    pedidoService.findPedidoById(pedidoId).ifPresent(p -> {});
                }
            } while (opcao != 0);
        }, () -> System.out.println("Pedido não encontrado."));
    }

    private void adicionarItemAoPedido(long pedidoId) {
        System.out.print("ID do produto a ser adicionado: ");
        long produtoId = lerLong();
        System.out.print("Quantidade: ");
        int quantidade = lerInteiro();
        System.out.print("Preço de venda (pode ser diferente do cadastro): ");
        double precoVenda = lerDouble();
        pedidoService.adicionarItem(pedidoId, produtoId, quantidade, precoVenda);
    }

    private void removerItemDoPedido(long pedidoId) {
        System.out.print("ID do produto a ser removido: ");
        long produtoId = lerLong();
        pedidoService.removerItem(pedidoId, produtoId);
    }

    private void alterarQuantidadeItem(long pedidoId) {
        System.out.print("ID do produto para alterar a quantidade: ");
        long produtoId = lerLong();
        System.out.print("Nova quantidade: ");
        int novaQuantidade = lerInteiro();
        pedidoService.alterarQuantidadeItem(pedidoId, produtoId, novaQuantidade);
    }

    private void verDetalhesPedido(Pedido pedido) {
        System.out.println("\n--- DETALHES DO PEDIDO #" + pedido.getId() + " ---");
        System.out.println(pedido);
        System.out.println("Itens:");
        if (pedido.getItens().isEmpty()) {
            System.out.println("Nenhum item no pedido.");
        } else {
            pedido.getItens().forEach(System.out::println);
        }
        System.out.println("---------------------------------");
    }

    private void listarPedidosPorCliente() {
        System.out.print("Digite o documento do cliente: ");
        String documento = scanner.nextLine();
        System.out.println("\n--- PEDIDOS DO CLIENTE " + documento + " ---");
        pedidoService.findPedidosByCliente(documento).forEach(System.out::println);
    }

    private void listarTodosOsPedidos() {
        System.out.println("\n--- TODOS OS PEDIDOS ---");
        pedidoService.listarTodos().forEach(System.out::println);
    }

    private int lerInteiro() {
        try {
            int valor = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha
            return valor;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer
            return -1; // Retorna um valor inválido para repetir o loop
        }
    }

    private double lerDouble() {
        try {
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Consome a nova linha
            return valor;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número (use vírgula para decimais).");
            scanner.nextLine(); // Limpa o buffer
            return -1.0;
        }
    }

    private long lerLong() {
        try {
            long valor = scanner.nextLong();
            scanner.nextLine(); // Consome a nova linha
            return valor;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número longo.");
            scanner.nextLine(); // Limpa o buffer
            return -1L;
        }
    }
}