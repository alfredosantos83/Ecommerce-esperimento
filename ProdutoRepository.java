package com.ecommerce.repository;

import com.ecommerce.model.Produto;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProdutoRepository {
    private static final Path ARQUIVO = Paths.get("produtos.csv");

    public void salvar(List<Produto> produtos) {
        List<String> linhas = produtos.stream()
                .map(p -> p.getId() + "," + p.getNome() + "," + p.getPreco())
                .collect(Collectors.toList());

        try {
            Files.write(ARQUIVO, linhas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public List<Produto> carregar() {
        if (!Files.exists(ARQUIVO)) {
            return new ArrayList<>();
        }

        try (Stream<String> stream = Files.lines(ARQUIVO)) {
            List<Produto> produtos = stream
                    .map(linha -> {
                        String[] partes = linha.split(",");
                        long id = Long.parseLong(partes[0]);
                        String nome = partes[1];
                        double preco = Double.parseDouble(partes[2]);
                        return new Produto(id, nome, preco);
                    })
                    .collect(Collectors.toList());

            // Atualiza o gerador de ID para evitar colisões
            produtos.stream().mapToLong(Produto::getId).max().ifPresent(Produto::setLastId);
            return produtos;
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}