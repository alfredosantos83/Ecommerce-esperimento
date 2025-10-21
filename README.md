# Projeto E‑Commerce (Console)

Aplicação console em Java que implementa um pequeno sistema de e‑commerce com persistência em CSV.

## Pré-requisitos
- JDK 11+ instalado
- PowerShell (Windows)

## Compilação (PowerShell)
Abra um PowerShell na raiz do projeto (`c:\Users\Pichau\workspace-ada\projeto-ecommerce-java`) e execute:

```powershell
javac -d "bin" "*.java"
```

## Executando a aplicação
- Iniciar o menu (interativo):

```powershell
java -cp "bin" com.ecommerce.main.Main
```

- Iniciar e sair automaticamente (envia `0` para sair):

```powershell
"0" | java -cp "bin" com.ecommerce.main.Main
```

- Executar o SmokeTest (fluxo automático de verificação de persistência):

```powershell
java -cp "bin" SmokeTest
```

## Arquivos de persistência gerados (na raiz do projeto)
- `clientes.csv` — clientes (documento,nome)
- `produtos.csv` — produtos (id,nome,preco)
- `pedidos.csv` — pedidos (id,documento,data,status)
- `itens_pedido.csv` — itens (pedidoId,produtoId,quantidade,precoVenda)

## Notas
- As notificações são simuladas com `CompletableFuture` e aparecem como mensagens assíncronas no console.
- Se quiser reiniciar os dados, apague os CSVs listados acima.

## Repositório remoto usado aqui:
https://github.com/alfredosantos83/Ecommerce-esperimento.git
