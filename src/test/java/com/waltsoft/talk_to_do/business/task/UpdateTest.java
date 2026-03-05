package com.waltsoft.talk_to_do.business.task;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import com.waltsoft.talk_to_do.dto.task.TaskUpdateDto;
import com.waltsoft.talk_to_do.entity.task.Task;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
class Update extends PostgreSQLContainerTest {

    private final String CATEGORY_CODE = "WORK";
    private final TaskPriorityCodeEnum PRIORITY_CODE = TaskPriorityCodeEnum.HIGH;
    private final TaskStatusCodeEnum STATUS_CODE = TaskStatusCodeEnum.IN_PROGRESS;
    private final String TITLE = "Make New Project";
    private final String DESCRIPTION = "Make project to detect fire in camera images";
    private final LocalDateTime START_DATE_TIME = LocalDateTime.now();

    private final LocalDateTime END_DATE_TIME = LocalDateTime
            .now()
            .plusHours(1);

    private final String CATEGORY_CODE_UPDATE = "FITNESS";
    private final TaskPriorityCodeEnum PRIORITY_CODE_UPDATE = TaskPriorityCodeEnum.LOW;
    private final TaskStatusCodeEnum STATUS_CODE_UPDATE = TaskStatusCodeEnum.PENDING;
    private final String TITLE_UPDATE = "Morning Run";
    private final String DESCRIPTION_UPDATE = "Run 28 km";

    private final LocalDateTime START_DATE_TIME_UPDATE = LocalDateTime
            .now()
            .plusHours(2);

    private final LocalDateTime END_DATE_TIME_UPDATE = LocalDateTime
            .now()
            .plusHours(3);


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

    private TaskUpdateDto taskUpdateDto;

    @BeforeEach
    void setup() {
        TaskCategory taskCategory = new TaskCategory().setCode(CATEGORY_CODE);
        taskCategory = taskCategoryService.save(taskCategory);

        TaskCategory taskCategoryUpdate = new TaskCategory().setCode(CATEGORY_CODE_UPDATE);
        taskCategoryUpdate = taskCategoryService.save(taskCategoryUpdate);

        Task task = new Task()
                .setTitle(TITLE)
                .setDescription(DESCRIPTION)
                .setCategory(taskCategory)
                .setPriority(taskPriorityService.findByCode(PRIORITY_CODE))
                .setStatus(taskStatusService.findByCode(STATUS_CODE))
                .setStartDateTime(START_DATE_TIME)
                .setEndDateTime(END_DATE_TIME);

        task = taskService.save(task);

        taskUpdateDto = new TaskUpdateDto(task.getId(),
                STATUS_CODE_UPDATE,
                taskCategoryUpdate.getId(),
                PRIORITY_CODE_UPDATE,
                TITLE_UPDATE,
                DESCRIPTION_UPDATE,
                START_DATE_TIME_UPDATE.toString(),
                END_DATE_TIME_UPDATE.toString());
    }

    @Test
    @DisplayName("Should update task")
    void shouldUpdateTask() {
        taskService.update(taskUpdateDto);
        List<Task> foundTaskSet = taskService.findAll();

        Assertions.assertNotNull(foundTaskSet);
        Assertions.assertEquals(1, foundTaskSet.size());

        Task task = foundTaskSet
                .stream()
                .findFirst()
                .get();

        Assertions.assertEquals(TITLE_UPDATE, task.getTitle());
        Assertions.assertEquals(DESCRIPTION_UPDATE, task.getDescription());

        Assertions.assertEquals(CATEGORY_CODE_UPDATE, task
                .getCategory()
                .getCode());

        Assertions.assertEquals(STATUS_CODE_UPDATE, task
                .getStatus()
                .getCode());

        Assertions.assertEquals(PRIORITY_CODE_UPDATE, task
                .getPriority()
                .getCode());

        Assertions.assertEquals(START_DATE_TIME_UPDATE, task.getStartDateTime());
        Assertions.assertEquals(END_DATE_TIME_UPDATE, task.getEndDateTime());

        Mockito
                .verify(eventPublisher, Mockito.times(1))
                .publishEvent(task);
    }
}