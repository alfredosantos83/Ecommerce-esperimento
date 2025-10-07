package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.util.NotificacaoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoService {
    private final List<Pedido> pedidos;
    private final PedidoRepository repository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final NotificacaoService notificacaoService;

    public PedidoService(ClienteService clienteService, ProdutoService produtoService) {
        this.repository = new PedidoRepository();
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.notificacaoService = new NotificacaoService();
        this.pedidos = repository.carregar(clienteService, produtoService);
    }

    public Optional<Pedido> criarPedido(String documentoCliente) {
        Optional<Cliente> clienteOpt = clienteService.findByDocumento(documentoCliente);
        if (clienteOpt.isEmpty()) {
            System.out.println("Erro: Cliente não encontrado.");
            return Optional.empty();
        }

        Pedido novoPedido = new Pedido(clienteOpt.get());
        pedidos.add(novoPedido);
        salvarDados();
        System.out.println("Novo pedido criado com ID: " + novoPedido.getId());
        return Optional.of(novoPedido);
    }

    public void adicionarItem(long pedidoId, long produtoId, int quantidade, double precoVenda) {
        findPedidoById(pedidoId).ifPresentOrElse(pedido -> {
            produtoService.findById(produtoId).ifPresentOrElse(produto -> {
                ItemPedido item = new ItemPedido(produto, quantidade, precoVenda);
                pedido.adicionarItem(item);
                salvarDados();
                System.out.println("Item adicionado ao pedido.");
            }, () -> System.out.println("Produto não encontrado."));
        }, () -> System.out.println("Pedido não encontrado."));
    }

    public void removerItem(long pedidoId, long produtoId) {
        findPedidoById(pedidoId).ifPresentOrElse(pedido -> {
            produtoService.findById(produtoId).ifPresentOrElse(produto -> {
                pedido.removerItem(produto);
                salvarDados();
                System.out.println("Item removido do pedido.");
            }, () -> System.out.println("Produto não encontrado no sistema."));
        }, () -> System.out.println("Pedido não encontrado."));
    }

    public void alterarQuantidadeItem(long pedidoId, long produtoId, int novaQuantidade) {
        findPedidoById(pedidoId).ifPresentOrElse(pedido -> {
            pedido.findItemByProdutoId(produtoId).ifPresentOrElse(item -> {
                item.setQuantidade(novaQuantidade);
                salvarDados();
                System.out.println("Quantidade do item alterada.");
            }, () -> System.out.println("Item não encontrado no pedido."));
        }, () -> System.out.println("Pedido não encontrado."));
    }

    public void finalizarPedido(long pedidoId) {
        findPedidoById(pedidoId).ifPresentOrElse(pedido -> {
            if (pedido.getItens().isEmpty() || pedido.getValorTotal() <= 0) {
                System.out.println("Erro: O pedido deve ter ao menos um item e valor maior que zero para ser finalizado.");
                return;
            }
            if (pedido.getStatus() == StatusPedido.ABERTO) {
                pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
                salvarDados();
                notificacaoService.notificar(pedido, "aguardando pagamento");
                System.out.println("Pedido finalizado. Aguardando pagamento.");
            } else {
                System.out.println("Este pedido não pode ser finalizado (status atual: " + pedido.getStatus() + ").");
            }
        }, () -> System.out.println("Pedido não encontrado."));
    }

    public void pagarPedido(long pedidoId) {
        findPedidoById(pedidoId).ifPresentOrElse(pedido -> {
            if (pedido.getStatus() == StatusPedido.AGUARDANDO_PAGAMENTO) {
                pedido.setStatus(StatusPedido.PAGO);
                salvarDados();
                notificacaoService.notificar(pedido, "pago com sucesso");
                System.out.println("Pagamento do pedido realizado com sucesso.");
            } else {
                System.out.println("Este pedido não pode ser pago (status atual: " + pedido.getStatus() + ").");
            }
        }, () -> System.out.println("Pedido não encontrado."));
    }

    public void entregarPedido(long pedidoId) {
        findPedidoById(pedidoId).ifPresentOrElse(pedido -> {
            if (pedido.getStatus() == StatusPedido.PAGO) {
                pedido.setStatus(StatusPedido.FINALIZADO);
                salvarDados();
                notificacaoService.notificar(pedido, "enviado para entrega");
                System.out.println("Pedido enviado para entrega.");
            } else {
                System.out.println("Este pedido não pode ser entregue (status atual: " + pedido.getStatus() + ").");
            }
        }, () -> System.out.println("Pedido não encontrado."));
    }

    public List<Pedido> findPedidosByCliente(String documento) {
        return pedidos.stream()
                .filter(p -> p.getCliente().getDocumento().equals(documento))
                .collect(Collectors.toList());
    }

    public Optional<Pedido> findPedidoById(long id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    private void salvarDados() {
        repository.salvar(pedidos);
    }
}