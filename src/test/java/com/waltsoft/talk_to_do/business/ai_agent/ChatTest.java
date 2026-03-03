package com.waltsoft.talk_to_do.business.ai_agent;

import com.waltsoft.talk_to_do.business.task.TaskService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.container.PostgreSQLContainerTest;
import com.waltsoft.talk_to_do.dot_env.DotEnv;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Consumer;


@ImportAutoConfiguration(exclude = {
        org.springframework.ai.model.google.genai.autoconfigure.chat.GoogleGenAiChatAutoConfiguration.class,
        org.springframework.ai.model.chat.client.autoconfigure.ChatClientAutoConfiguration.class
})
class ChatTest extends PostgreSQLContainerTest {

    private static final String USERNAME = "xxx";
    private static final String SYSTEM_CONTEXT = "Context test";
    private static final String USER_INPUT = "When is my doctor?";
    private static final String AI_OUTPUT = "Your doctor's appointment is scheduled for 2026-03-03 at 06:00.";

    @MockitoSpyBean
    private AIAgentService aiAgentService;
    @MockitoBean
    private TaskService taskService;
    @MockitoBean
    private TaskCategoryService taskCategoryService;
    @MockitoBean
    private TaskPriority taskPriority;
    @MockitoBean
    private TaskStatus taskStatus;
    @MockitoBean
    private ChatClient chatClient;
    @MockitoBean
    private DotEnv dotEnv;
    @MockitoBean(name = "promptSystemContext")
    private Resource promptSystemContext;

    private Consumer<ChatClient.PromptSystemSpec> system;
    private Consumer<ChatClient.AdvisorSpec> advisors;

    @BeforeEach
    void setup() throws IOException {
        Mockito
                .when(promptSystemContext.getInputStream())
                .thenReturn(new ByteArrayInputStream(SYSTEM_CONTEXT.getBytes()));

        Mockito
                .when(dotEnv.getUsername())
                .thenReturn(USERNAME);
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
                .when(requestSpec.system(Mockito.any(Consumer.class)))
                .thenReturn(requestSpec);

        Mockito
                .when(requestSpec.tools(taskService, taskCategoryService))
                .thenReturn(requestSpec);

        Mockito
                .when(requestSpec.advisors(Mockito.any(Consumer.class)))
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

        Mockito
                .verify(aiAgentService, org.mockito.Mockito.times(1))
                .makeAdvisors(USERNAME);

        Mockito
                .verify(aiAgentService, org.mockito.Mockito.times(1))
                .makeSystem(promptSystemContext);
    }
}