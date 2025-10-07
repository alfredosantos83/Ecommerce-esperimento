package com.ecommerce.service;

import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdutoService {
    private final List<Produto> produtos;
    private final ProdutoRepository repository;

    public ProdutoService() {
        this.repository = new ProdutoRepository();
        this.produtos = repository.carregar();
    }

    public void cadastrarProduto(String nome, double preco) {
        Produto novoProduto = new Produto(nome, preco);
        produtos.add(novoProduto);
        salvarDados();
        System.out.println("Produto cadastrado com sucesso!");
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public Optional<Produto> findById(long id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public List<Produto> findByNome(String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void atualizarProduto(long id, String novoNome, double novoPreco) {
        findById(id).ifPresentOrElse(produto -> {
            produto.setNome(novoNome);
            produto.setPreco(novoPreco);
            salvarDados();
            System.out.println("Produto atualizado com sucesso!");
        }, () -> {
            System.out.println("Produto não encontrado.");
        });
    }

    private void salvarDados() {
        repository.salvar(produtos);
    }
}