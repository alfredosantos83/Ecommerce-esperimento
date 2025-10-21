package com.ecommerce.repository;

import com.ecommerce.model.*;
import com.ecommerce.service.ClienteService;
import com.ecommerce.service.ProdutoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PedidoRepository {
    private static final Path ARQUIVO_PEDIDOS = Paths.get("pedidos.csv");
    private static final Path ARQUIVO_ITENS = Paths.get("itens_pedido.csv");

    public void salvar(List<Pedido> pedidos) {
        // Salva os pedidos
        List<String> linhasPedidos = pedidos.stream()
                .map(p -> String.join(",",
                        String.valueOf(p.getId()),
                        p.getCliente().getDocumento(),
                        p.getDataCriacao().toString(),
                        p.getStatus().name()))
                .collect(Collectors.toList());

        try {
            Files.write(ARQUIVO_PEDIDOS, linhasPedidos, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Erro ao salvar pedidos: " + e.getMessage());
        }

        // Salva os itens de todos os pedidos
        List<String> linhasItens = pedidos.stream()
                .flatMap(p -> p.getItens().stream()
                        .map(item -> String.join(",",
                                String.valueOf(p.getId()),
                                String.valueOf(item.getProduto().getId()),
                                String.valueOf(item.getQuantidade()),
                                String.valueOf(item.getPrecoVenda()))))
                .collect(Collectors.toList());

        try {
            Files.write(ARQUIVO_ITENS, linhasItens, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Erro ao salvar itens de pedido: " + e.getMessage());
        }
    }

    public List<Pedido> carregar(ClienteService clienteService, ProdutoService produtoService) {
        if (!Files.exists(ARQUIVO_PEDIDOS)) {
            return new ArrayList<>();
        }

        try (Stream<String> stream = Files.lines(ARQUIVO_PEDIDOS)) {
            List<Pedido> pedidos = stream.map(linha -> {
                String[] p = linha.split(",");
                long id = Long.parseLong(p[0]);
                Optional<Cliente> clienteOpt = clienteService.findByDocumento(p[1]);
                LocalDateTime data = LocalDateTime.parse(p[2]);
                StatusPedido status = StatusPedido.valueOf(p[3]);

                return clienteOpt.map(cliente -> new Pedido(id, cliente, data, status)).orElse(null);
            }).filter(p -> p != null).collect(Collectors.toList());

            // Carrega e adiciona os itens aos pedidos
            if (Files.exists(ARQUIVO_ITENS)) {
                try (Stream<String> itensStream = Files.lines(ARQUIVO_ITENS)) {
                    itensStream.forEach(linhaItem -> {
                        String[] i = linhaItem.split(",");
                        long pedidoId = Long.parseLong(i[0]);
                        long produtoId = Long.parseLong(i[1]);
                        int quantidade = Integer.parseInt(i[2]);
                        double precoVenda = Double.parseDouble(i[3]);

                        pedidos.stream().filter(p -> p.getId() == pedidoId).findFirst().ifPresent(pedido -> {
                            produtoService.findById(produtoId).ifPresent(produto -> {
                                pedido.adicionarItem(new ItemPedido(produto, quantidade, precoVenda));
                            });
                        });
                    });
                }
            }
            pedidos.stream().mapToLong(Pedido::getId).max().ifPresent(Pedido::setLastId);
            return pedidos;
        } catch (IOException e) {
            System.err.println("Erro ao carregar pedidos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}