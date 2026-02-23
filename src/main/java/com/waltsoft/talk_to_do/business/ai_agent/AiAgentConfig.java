package com.waltsoft.talk_to_do.business.ai_agent;

import com.google.genai.Client;
import com.waltsoft.talk_to_do.dot_env.DotEnv;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;

import java.util.function.Function;

@Configuration
public class AiAgentConfig {

    private static final int MAX_MESSAGES_CHAT_MEMORY = 10;

    private final DotEnv dotEnv;

    @Autowired
    public AiAgentConfig(DotEnv dotEnv) {
        this.dotEnv = dotEnv;
    }

    @Bean
    public ChatMemory chatMemory() {
        ChatMemoryRepository repository = new InMemoryChatMemoryRepository();

        return MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(repository)
                .maxMessages(MAX_MESSAGES_CHAT_MEMORY)
                .build();
    }

    @Bean
    @Description("Adiciona uma nova tarefa à lista de afazeres")
    public Function<TaskRequest, String> criarTarefa() {
        return request -> {
            System.out.println("\n[SISTEMA] Executando função: Criar tarefa '" + request.nome() + "'");
            return "Sucesso: Tarefa '" + request.nome() + "' agendada.";
        };
    }

    @Bean
    @Primary
    public GoogleGenAiChatModel createCustomChatModel() {

        //Just to satisfy the Spring API Client
        System.setProperty("spring.ai.google.genai.project-id", "unused");
        System.setProperty("spring.ai.google.genai.location", "us-central1");

        var client = Client
                .builder()
                .apiKey(dotEnv.getGeminiApiKey())
                .build();


        var options = GoogleGenAiChatOptions
                .builder()
                .model("gemini-2.5-flash")
                .temperature(0.7)
                .build();


        return GoogleGenAiChatModel
                .builder()
                .genAiClient(client)
                .defaultOptions(options)
                .build();
    }


    public record TaskRequest(String nome) {
    }
}