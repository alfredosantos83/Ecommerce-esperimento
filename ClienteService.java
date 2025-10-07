package com.ecommerce.service;

import com.ecommerce.model.Cliente;
import com.ecommerce.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClienteService {
    private final List<Cliente> clientes;
    private final ClienteRepository repository;

    public ClienteService() {
        this.repository = new ClienteRepository();
        this.clientes = repository.carregar();
    }

    public void cadastrarCliente(String nome, String documento) {
        if (findByDocumento(documento).isPresent()) {
            System.out.println("Erro: Já existe um cliente com este documento.");
            return;
        }
        Cliente novoCliente = new Cliente(nome, documento);
        clientes.add(novoCliente);
        salvarDados();
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public Optional<Cliente> findByDocumento(String documento) {
        return clientes.stream()
                .filter(c -> c.getDocumento().equals(documento))
                .findFirst();
    }

    public List<Cliente> findByNome(String nome) {
        return clientes.stream()
                .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void atualizarCliente(String documento, String novoNome) {
        findByDocumento(documento).ifPresentOrElse(cliente -> {
            cliente.setNome(novoNome);
            salvarDados();
            System.out.println("Cliente atualizado com sucesso!");
        }, () -> {
            System.out.println("Cliente não encontrado.");
        });
    }

    private void salvarDados() {
        repository.salvar(clientes);
    }
}