package com.waltsoft.talk_to_do.business.ai_agent;

import com.waltsoft.talk_to_do.business.task.TaskService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.dot_env.DotEnv;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class AIAgentService {

    public static final String CURRENT_DATE_TIME_PROMPT_PARAM = "CURRENT_DATE_TIME";
    private final TaskService taskService;
    private final TaskCategoryService taskCategoryService;
    private final ChatClient chatClient;
    private final String username;
    @Value("classpath:/prompts/system-context.md")
    private Resource promptSystemContext;

    @Autowired
    public AIAgentService(ChatClient.Builder chatClientBuilder,
                          ChatMemory chatMemory,
                          DotEnv dotEnv,
                          TaskService taskService,
                          TaskCategoryService taskCategoryService) {
        this(makeChatClient(chatClientBuilder, chatMemory),
                dotEnv.getUsername(),
                taskService,
                taskCategoryService);
    }

    AIAgentService(ChatClient chatClient,
                   String username,
                   TaskService taskService,
                   TaskCategoryService taskCategoryService) {
        this.chatClient = chatClient;
        this.username = username;
        this.taskService = taskService;
        this.taskCategoryService = taskCategoryService;
    }


    private static ChatClient makeChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build())
                .build();
    }

    @Cacheable(value = "aiChatCache", key = "#userInput")
    public String chat(String userInput) {

        return chatClient
                .prompt()
                .user(userInput)
                .system(makeSystem(promptSystemContext))
                .tools(this.taskService, this.taskCategoryService)
                .advisors(makeAdvisors(username))
                .call()
                .content();
    }

    Consumer<ChatClient.AdvisorSpec> makeAdvisors(String username) {
        return a -> a.param(ChatMemory.CONVERSATION_ID, username);
    }

    Consumer<ChatClient.PromptSystemSpec> makeSystem(Resource promptSystemContext) {
        return s -> s
                .text(promptSystemContext)
                .params(Map.of(CURRENT_DATE_TIME_PROMPT_PARAM, LocalDateTime
                        .now()
                        .toString()));
    }


}
