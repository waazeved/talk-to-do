package com.waltsoft.talk_to_do.business.ai_agent;

import com.waltsoft.talk_to_do.business.task.TaskService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.dot_env.DotEnv;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final DotEnv dotEnv;
    private final Resource promptSystemContext;

    @Autowired
    public AIAgentService(ChatClient chatClient,
                          DotEnv dotEnv,
                          TaskService taskService,
                          TaskCategoryService taskCategoryService,
                          Resource promptSystemContext) {
        this.chatClient = chatClient;
        this.dotEnv = dotEnv;
        this.taskService = taskService;
        this.taskCategoryService = taskCategoryService;
        this.promptSystemContext = promptSystemContext;
    }

    @Cacheable(value = "aiChatCache", key = "#userInput")
    public String chat(String userInput) {

        return chatClient
                .prompt()
                .user(userInput)
                .system(makeSystem(promptSystemContext))
                .tools(this.taskService, this.taskCategoryService)
                .advisors(makeAdvisors(dotEnv.getUsername()))
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
