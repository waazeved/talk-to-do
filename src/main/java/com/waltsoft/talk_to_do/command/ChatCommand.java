package com.waltsoft.talk_to_do.command;


import com.waltsoft.talk_to_do.dot_env.DotEnv;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import picocli.CommandLine;

import java.util.Scanner;

@CommandLine.Command(name = ChatCommand.COMMAND,
        description = "Start chat with A.I. agent.",
        mixinStandardHelpOptions = true)
public class ChatCommand implements Runnable {

    static final String COMMAND = "start";
    private static final Log LOGGER = LogFactory.getLog(ChatCommand.class.getName());

    private final ChatClient chatClient;
    private final String username;

    public ChatCommand(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, DotEnv dotEnv) {
        this.chatClient = makeChatClient(chatClientBuilder, chatMemory);
        this.username = dotEnv.getUsername(); //In the future, the user will authenticate to enter in the system and username will come from database
    }


    private ChatClient makeChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build())
                .build();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n========================================");
        System.out.println("   AI AGENT PRONTO - Digite sua mensagem");
        System.out.println("========================================\n");

        while (true) {
            System.out.print("VOCÊ > ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("sair")) {
                System.out.println("AI > Até mais!");
                break;
            }

            try {
                String response = chatClient
                        .prompt()
                        .user(input)
                        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, this.username))
                        .call()
                        .content();

                System.out.println("AI > " + response + "\n");
            } catch (Exception e) {
                System.out.println("AI > Erro: " + e.getMessage());
            }
        }
    }
}