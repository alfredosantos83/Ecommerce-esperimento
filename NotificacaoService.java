
package com.ecommerce.util;

import com.ecommerce.model.Pedido;

import java.util.concurrent.CompletableFuture;

public class NotificacaoService {

    private void enviarEmail(String mensagem) {
        System.out.println("\n--- ENVIANDO NOTIFICAÇÃO ---");
        System.out.println(mensagem);
        System.out.println("----------------------------\n");
    }

    public void notificar(Pedido pedido, String etapa) {
        CompletableFuture.runAsync(() -> {
            enviarEmail(String.format("Olá, %s! Seu pedido #%d foi %s.", pedido.getCliente().getNome(), pedido.getId(), etapa));
        });
    }
}