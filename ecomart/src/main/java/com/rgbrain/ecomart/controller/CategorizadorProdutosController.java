package com.rgbrain.ecomart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorizador-produtos")
public class CategorizadorProdutosController {

    @Autowired
	private OpenAiChatModel chatModel;

    @GetMapping("")
    public ResponseEntity<String> categorizarProdutos(String produto) {
        UserMessage userMessage = new UserMessage(produto);

		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("""
            Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado

            Escolha uma categoria dentra a lista abaixo:

            * Higiene pessoal
            * Eletronicos
            * Esportes
            * Outros

            ###exemplo de uso###
            Pergunta: Bola de futebol
            Resposta: Esportes
        """);

		Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", "Bob", "voice", "pirate"));
		Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
		ChatResponse response = this.chatModel.call(prompt);

        return ResponseEntity.ok(response.getResults().get(0).getOutput().getContent());
    }
    
}
