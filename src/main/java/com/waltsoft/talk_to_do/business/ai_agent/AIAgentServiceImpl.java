package com.waltsoft.talk_to_do.business.ai_agent;

import com.waltsoft.talk_to_do.business.task.TaskService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import com.waltsoft.talk_to_do.dot_env.DotEnv;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
class AIAgentServiceImpl implements AIAgentService {

    private final TaskService taskService;
    private final TaskStatusService taskStatusService;
    private final TaskCategoryService taskCategoryService;
    private final TaskPriorityService taskPriorityService;
    private final ChatClient chatClient;
    private final String username;

    @Value("classpath:/prompts/system-context.md")
    private Resource promptSystemContext;

    @Autowired
    public AIAgentServiceImpl(ChatClient.Builder chatClientBuilder,
                              ChatMemory chatMemory,
                              DotEnv dotEnv,
                              TaskService taskService,
                              TaskStatusService taskStatusService,
                              TaskCategoryService taskCategoryService,
                              TaskPriorityService taskPriorityService) {
        this.chatClient = makeChatClient(chatClientBuilder, chatMemory);
        this.username = dotEnv.getUsername(); //In the future, the user will authenticate to enter in the system and username will come from database
        this.taskService = taskService;
        this.taskStatusService = taskStatusService;
        this.taskCategoryService = taskCategoryService;
        this.taskPriorityService = taskPriorityService;
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
    public String chat(String userInput) {
        return chatClient
                .prompt()
                .user(userInput)
                .system(promptSystemContext)
                .tools(this.taskService)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, this.username))
                .call()
                .content();
    }


}
