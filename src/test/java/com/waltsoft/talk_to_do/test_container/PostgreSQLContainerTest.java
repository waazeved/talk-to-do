package com.waltsoft.talk_to_do.test_container;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = {
        org.springframework.ai.model.google.genai.autoconfigure.chat.GoogleGenAiChatAutoConfiguration.class,
        org.springframework.ai.model.chat.client.autoconfigure.ChatClientAutoConfiguration.class
})
public abstract class PostgreSQLContainerTest {

    public static final String DOCKER_POSTGRES_IMAGE_NAME = "postgres:latest";
    
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DOCKER_POSTGRES_IMAGE_NAME);

    static {
        postgres.start();
    }

    @TestConfiguration
    static class ApplicationEventPublisherConfiguration {
        @Bean
        @Primary
        ApplicationEventPublisher publisher() {
            return Mockito.mock(ApplicationEventPublisher.class);
        }
    }
}
