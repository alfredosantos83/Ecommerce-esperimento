package com.ecommerce.model;

import java.util.Objects;

public class Cliente {
    private String nome;
    private final String documento; // CPF, RG, etc.

    public Cliente(String nome, String documento) {
        Objects.requireNonNull(documento, "Documento não pode ser nulo.");
        if (documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Documento não pode ser vazio.");
        }
        this.nome = nome;
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}