package com.waltsoft.talk_to_do.business.ai_agent;

import com.waltsoft.talk_to_do.business.task.TaskService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.function.Consumer;

class ChatTest {

    private static final String USERNAME = "xxx";
    private static final String SYSTEM_CONTEXT = "Context test";
    private static final String PROMPT_SYSTEM_CONTEXT_FIELD = "promptSystemContext";
    private static final String USER_INPUT = "When is my doctor?";
    private static final String AI_OUTPUT = "Your doctor's appointment is scheduled for 2026-03-03 at 06:00.";

    private AIAgentService aiAgentService;
    private TaskService taskService;
    private TaskCategoryService taskCategoryService;
    private ChatClient chatClient;
    private Resource resourceSystemContext;
    private Consumer<ChatClient.PromptSystemSpec> system;
    private Consumer<ChatClient.AdvisorSpec> advisors;

    @BeforeEach
    void setup() {
        chatClient = Mockito.mock(ChatClient.class);
        taskService = Mockito.mock(TaskService.class);
        taskCategoryService = Mockito.mock(TaskCategoryService.class);
        aiAgentService = Mockito.spy(new AIAgentService(chatClient, USERNAME, taskService, taskCategoryService));

        resourceSystemContext = new ByteArrayResource(SYSTEM_CONTEXT.getBytes());
        ReflectionTestUtils.setField(aiAgentService, PROMPT_SYSTEM_CONTEXT_FIELD, resourceSystemContext);

        system = aiAgentService.makeSystem(resourceSystemContext);
        advisors = aiAgentService.makeAdvisors(USERNAME);

        Mockito
                .when(aiAgentService.makeSystem(resourceSystemContext))
                .thenReturn(system);
        Mockito
                .when(aiAgentService.makeAdvisors(USERNAME))
                .thenReturn(advisors);

    }

    @Test
    @DisplayName("AIAgentService.chat should process input through spring context")
    void shouldProcessInputThroughSpringContextTest() {
        ChatClient.ChatClientRequestSpec requestSpec = Mockito.mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec responseSpec = Mockito.mock(ChatClient.CallResponseSpec.class);


        Mockito
                .when(chatClient.prompt())
                .thenReturn(requestSpec);

        Mockito
                .when(requestSpec.user(USER_INPUT))
                .thenReturn(requestSpec);

        Mockito
                .when(requestSpec.system(system))
                .thenReturn(requestSpec);

        Mockito
                .when(requestSpec.tools(taskService, taskCategoryService))
                .thenReturn(requestSpec);

        Mockito
                .when(requestSpec.advisors(advisors))
                .thenReturn(requestSpec);

        Mockito
                .when(requestSpec.call())
                .thenReturn(responseSpec);

        Mockito
                .when(responseSpec.content())
                .thenReturn(AI_OUTPUT);

        String response = aiAgentService.chat(USER_INPUT);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(AI_OUTPUT, response);

        Mockito
                .verify(chatClient, org.mockito.Mockito.times(1))
                .prompt();
    }
}