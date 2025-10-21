package com.ecommerce.model;

import java.util.concurrent.atomic.AtomicLong;

public class Produto {
    private static final AtomicLong idGenerator = new AtomicLong(0);

    private final long id;
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.id = idGenerator.incrementAndGet();
        this.nome = nome;
        this.preco = preco;
    }

    public Produto(long id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Permite atualizar o gerador de IDs quando os produtos são carregados do repositório
    public static void setLastId(long lastId) {
        idGenerator.set(lastId);
    }
}