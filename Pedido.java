package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Pedido {
    private static final AtomicLong idGenerator = new AtomicLong(0);

    private final long id;
    private final Cliente cliente;
    private final List<ItemPedido> itens;
    private final LocalDateTime dataCriacao;
    private StatusPedido status;

    public Pedido(Cliente cliente) {
        this.id = idGenerator.incrementAndGet();
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.ABERTO;
    }

    // Construtor para carregar do repositório
    public Pedido(long id, Cliente cliente, LocalDateTime dataCriacao, StatusPedido status) {
        this.id = id;
        this.cliente = cliente;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemPedido item) {
        if (this.status == StatusPedido.ABERTO) {
            itens.add(item);
        } else {
            System.out.println("Não é possível adicionar itens a um pedido que não está 'ABERTO'.");
        }
    }

    public void removerItem(Produto produto) {
        if (this.status == StatusPedido.ABERTO) {
            itens.removeIf(item -> item.getProduto().getId() == produto.getId());
        } else {
            System.out.println("Não é possível remover itens de um pedido que não está 'ABERTO'.");
        }
    }

    public Optional<ItemPedido> findItemByProdutoId(long produtoId) {
        return itens.stream()
                .filter(item -> item.getProduto().getId() == produtoId)
                .findFirst();
    }

    public double getValorTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    public long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens); // Retorna cópia para proteger a lista original
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Pedido [ID: %d, Cliente: %s, Data: %s, Status: %s, Valor Total: R$%.2f]",
                id, cliente.getNome(), dataCriacao, status, getValorTotal());
    }
}