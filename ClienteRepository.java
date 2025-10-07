package com.ecommerce.repository;

import com.ecommerce.model.Cliente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClienteRepository {
    private static final Path ARQUIVO = Paths.get("clientes.csv");

    public void salvar(List<Cliente> clientes) {
        List<String> linhas = clientes.stream()
                .map(c -> c.getDocumento() + "," + c.getNome())
                .collect(Collectors.toList());

        try {
            Files.write(ARQUIVO, linhas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    public List<Cliente> carregar() {
        if (!Files.exists(ARQUIVO)) {
            return new ArrayList<>();
        }

        try (Stream<String> stream = Files.lines(ARQUIVO)) {
            return stream
                    .map(linha -> {
                        String[] partes = linha.split(",");
                        return new Cliente(partes[1], partes[0]);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao carregar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}