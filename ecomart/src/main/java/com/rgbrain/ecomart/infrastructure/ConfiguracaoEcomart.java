package com.rgbrain.ecomart.infrastructure;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoEcomart {

    private static final String GROQ_BASE_URL = "https://api.groq.com/openai";

	private static final String DEFAULT_GROQ_MODEL = "llama3-70b-8192";

    @Bean
    public OpenAiApi chatCompletionApi() {
        return new OpenAiApi(GROQ_BASE_URL, System.getenv("GROQ_API_KEY"));
    }

    @Bean
    public OpenAiChatModel openAiClient(OpenAiApi openAiApi) {
        return new OpenAiChatModel(
            openAiApi, 
            OpenAiChatOptions.builder()
                .withModel(DEFAULT_GROQ_MODEL)
                .withTemperature(0.85)
            .build());
    }	
}
