package com.rgbrain.ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gerador-produtos")
public class GeradorProdutosController {

    private final ChatClient chatClient;

    public GeradorProdutosController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("")
    public ResponseEntity<String> gerarProdutos() {
        return ResponseEntity.ok(
            chatClient.prompt()
                .user("Gere 5 produtos ecologicos")
                .call()
                .content()
        );
    }
    
}
