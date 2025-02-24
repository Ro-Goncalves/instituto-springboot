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
@RequestMapping("/gerador-produtos")
public class GeradorProdutosController {

    @Autowired
	private OpenAiChatModel chatModel;

    @GetMapping("")
    public ResponseEntity<String> gerarProdutos() {
        UserMessage userMessage = new UserMessage(
				"TGere 5 produtos ecologicos.");
		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("Seu nome é Brian, seja sempre cortês e amigavel. Deve responder sempre em Português");
		Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", "Bob", "voice", "pirate"));
		Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
		ChatResponse response = this.chatModel.call(prompt);

        return ResponseEntity.ok(response.getResults().get(0).getOutput().getContent());
    }
    
}
