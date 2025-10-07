package com.ecommerce.model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoVenda;

    public ItemPedido(Produto produto, int quantidade, double precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public double getSubtotal() {
        return quantidade * precoVenda;
    }

    @Override
    public String toString() {
        return String.format("Produto: %s | Qtd: %d | Preço Venda: R$%.2f | Subtotal: R$%.2f",
                produto.getNome(), quantidade, precoVenda, getSubtotal());
    }
}