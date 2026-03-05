package com.waltsoft.talk_to_do.business.task;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import com.waltsoft.talk_to_do.dto.task.TaskDto;
import com.waltsoft.talk_to_do.entity.task.Task;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Transactional
class FindAllDtoTest extends PostgreSQLContainerTest {

    private final String CATEGORY_CODE = "WORK";
    private final TaskPriorityCodeEnum PRIORITY_CODE = TaskPriorityCodeEnum.HIGH;
    private final TaskStatusCodeEnum STATUS_CODE = TaskStatusCodeEnum.IN_PROGRESS;
    private final String TITLE = "Make New Project";
    private final String DESCRIPTION = "Make project to detect fire in camera images";
    private final LocalDateTime START_DATE_TIME = LocalDateTime.now();

    private final LocalDateTime END_DATE_TIME = LocalDateTime
            .now()
            .plusHours(1);

    @MockitoSpyBean
    private TaskService taskService;
    @Autowired
    private TaskPriorityService taskPriorityService;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private TaskCategoryService taskCategoryService;
    @MockitoBean
    private ApplicationEventPublisher eventPublisher;
    @MockitoBean
    private AIAgentService aiAgentService;

    private Task task;

    @BeforeEach
    void setup() {
        TaskCategory taskCategory = new TaskCategory().setCode(CATEGORY_CODE);
        taskCategory = taskCategoryService.save(taskCategory);

        task = new Task()
                .setTitle(TITLE)
                .setDescription(DESCRIPTION)
                .setCategory(taskCategory)
                .setPriority(taskPriorityService.findByCode(PRIORITY_CODE))
                .setStatus(taskStatusService.findByCode(STATUS_CODE))
                .setStartDateTime(START_DATE_TIME)
                .setEndDateTime(END_DATE_TIME);

        task = taskService.save(task);
    }

    @Test
    @DisplayName("Should find all TaskDto")
    void shouldFindAllTaskDto() {

        Set<TaskDto> foundTaskDtoSet = this.taskService.findAllDto();

        Assertions.assertNotNull(foundTaskDtoSet);
        Assertions.assertEquals(1, foundTaskDtoSet.size());

        TaskDto taskDto = foundTaskDtoSet
                .stream()
                .findFirst()
                .get();

        Assertions.assertEquals(task.getId(), taskDto.id());
        Assertions.assertEquals(TITLE, taskDto.title());
        Assertions.assertEquals(DESCRIPTION, taskDto.description());

        Assertions.assertEquals(CATEGORY_CODE, taskCategoryService
                .findById(taskDto.taskCategoryId())
                .orElseThrow()
                .getCode());

        Assertions.assertEquals(STATUS_CODE, taskDto.taskStatusCode());
        Assertions.assertEquals(PRIORITY_CODE, taskDto.taskPriorityCode());
        Assertions.assertEquals(START_DATE_TIME.toString(), taskDto.startDateTime());
        Assertions.assertEquals(END_DATE_TIME.toString(), taskDto.endDateTime());

    }
}