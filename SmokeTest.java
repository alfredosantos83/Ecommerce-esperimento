import com.ecommerce.service.ClienteService;
import com.ecommerce.service.PedidoService;
import com.ecommerce.service.ProdutoService;

public class SmokeTest {
    public static void main(String[] args) {
        System.out.println("=== SmokeTest START ===");
        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();
        PedidoService pedidoService = new PedidoService(clienteService, produtoService);

        // Cadastrar cliente
        clienteService.cadastrarCliente("Teste Cliente", "123456789");
        // Cadastrar produtos
        produtoService.cadastrarProduto("Produto A", 10.0);
        produtoService.cadastrarProduto("Produto B", 20.0);

        System.out.println("Produtos cadastrados:");
        produtoService.listarProdutos().forEach(p -> System.out.println(p));

        // Criar pedido e adicionar itens
        pedidoService.criarPedido("123456789");
        long pedidoId = pedidoService.listarTodos().get(0).getId();
        long produtoAId = produtoService.listarProdutos().get(0).getId();
        long produtoBId = produtoService.listarProdutos().get(1).getId();

        pedidoService.adicionarItem(pedidoId, produtoAId, 2, 9.0);
        pedidoService.adicionarItem(pedidoId, produtoBId, 1, 19.0);

        System.out.println("Pedido criado e itens adicionados:");
        pedidoService.findPedidoById(pedidoId).ifPresent(System.out::println);

        // Finalizar e pagar
        pedidoService.finalizarPedido(pedidoId);
        pedidoService.pagarPedido(pedidoId);

        System.out.println("Após pagamento:");
        pedidoService.findPedidoById(pedidoId).ifPresent(System.out::println);

        // Re-instanciar serviços para forçar reload do CSV
        clienteService = new ClienteService();
        produtoService = new ProdutoService();
        pedidoService = new PedidoService(clienteService, produtoService);

        System.out.println("Após reload — Produtos carregados:");
        produtoService.listarProdutos().forEach(p -> System.out.println(p));

        System.out.println("Após reload — Pedidos carregados:");
        pedidoService.listarTodos().forEach(p -> System.out.println(p));

        System.out.println("=== SmokeTest END ===");
    }
}
