# Projeto E-Commerce em Java

Este é um sistema de E-Commerce simples desenvolvido em Java, como parte de um projeto de estudo. A aplicação é executada via console and permite gerenciar clientes, produtos e pedidos, persistindo os dados em arquivos CSV.

## Funcionalidades

- **Gestão de Clientes**: Cadastrar, listar, atualizar e pesquisar clientes.
- **Gestão de Produtos**: Cadastrar, listar, atualizar e pesquisar produtos.
- **Gestão de Pedidos**:
  - Criar pedidos para clientes.
  - Adicionar/remover itens.
  - Alterar o fluxo de status do pedido (Aberto -> Aguardando Pagamento -> Pago -> Finalizado).
  - Notificações assíncronas simuladas para o cliente.

## Tecnologias e Conceitos Utilizados

- **Linguagem**: Java 11+
- **APIs e Conceitos**:
  - `java.time` para manipulação de datas.
  - `Stream API` para manipulação de coleções (map, filter, reduce).
  - `Optional` para tratamento de valores nulos.
  - `CompletableFuture` para simulação de notificações assíncronas.
  - `Scanner` para interação via menu de console.
- **Persistência**: Arquivos de texto no formato CSV.

## Como Executar o Projeto

1.  **Pré-requisitos**:
    - JDK (Java Development Kit) 11 ou superior instalado.

2.  **Clone o repositório**:
    ```bash
    git clone https://github.com/alfredosantos83/projeto-ecommerce-java.git
    cd projeto-ecommerce-java
    ```

3.  **Compile e execute o projeto a partir da raiz**:
    ```bash
    javac -d bin src/com/ecommerce/main/Main.java
    java -cp bin com.ecommerce.main.Main
    ```
