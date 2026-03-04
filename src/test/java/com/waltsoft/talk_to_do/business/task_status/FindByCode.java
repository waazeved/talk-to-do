package com.waltsoft.talk_to_do.business.task_status;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.stream.Stream;


class FindByCode extends PostgreSQLContainerTest {

    @MockitoSpyBean
    private TaskStatusService taskStatusService;
    @MockitoBean
    private AIAgentService aiAgentService;

    static Stream<TaskStatusCodeEnum> shouldFindTaskStatusByCode() {
        return Stream.of(TaskStatusCodeEnum.values());
    }

    @ParameterizedTest
    @MethodSource("shouldFindTaskStatusByCode")
    @DisplayName("Should find TaskSTatus by code")
    void shouldFindTaskStatusByCode(TaskStatusCodeEnum code) {
        TaskStatus taskStatus = taskStatusService.findByCode(code);
        Assertions.assertNotNull(taskStatus);

        Mockito
                .verify(taskStatusService, Mockito.times(0))
                .findAllMappedByCode();
    }
}