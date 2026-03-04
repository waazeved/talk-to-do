package com.waltsoft.talk_to_do.business.task_priority;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
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
    private TaskPriorityService taskPriorityService;
    @MockitoBean
    private AIAgentService aiAgentService;

    static Stream<TaskPriorityCodeEnum> shouldFindTaskPriorityByCode() {
        return Stream.of(TaskPriorityCodeEnum.values());
    }

    @ParameterizedTest
    @MethodSource("shouldFindTaskPriorityByCode")
    @DisplayName("Should find TaskPriority by code")
    void shouldFindTaskPriorityByCode(TaskPriorityCodeEnum code) {
        TaskPriority taskPriority = taskPriorityService.findByCode(code);
        Assertions.assertNotNull(taskPriority);

        Mockito
                .verify(taskPriorityService, Mockito.times(0))
                .findAllMappedByCode();
    }
}