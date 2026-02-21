package com.waltsoft.talk_to_do.entity.task;


import com.waltsoft.talk_to_do.entity.BasicEntity;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task extends BasicEntity {

    public static final String TABLE_NAME = "TASK";
    public static final String TASK_STATUS_ID_COLUMN = "TASK_STATUS_ID";
    public static final String TASK_CATEGORY_ID_COLUMN = "TASK_CATEGORY_ID";
    public static final String TASK_PRIORITY_ID_COLUMN = "TASK_PRIORITY_ID";
    public static final String TITLE_COLUMN = "TITLE";
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String START_DATE_TIME_COLUMN = "START_DATE_TIME";
    public static final String END_DATE_TIME_COLUMN = "END_DATE_TIME";

    private static final long serialVersionUID = 1572983257823161513L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = TASK_STATUS_ID_COLUMN)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = TASK_CATEGORY_ID_COLUMN)
    private TaskCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = TASK_PRIORITY_ID_COLUMN)
    private TaskPriority priority;

    @Column(name = TITLE_COLUMN)
    private String title;

    @Column(name = DESCRIPTION_COLUMN)
    private String description;

    @Column(name = START_DATE_TIME_COLUMN)
    private LocalDateTime startDateTime;

    @Column(name = END_DATE_TIME_COLUMN)
    private LocalDateTime endDateTime;

    public Task() {
    }

    public Task(final Long id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Task setStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public Task setCategory(TaskCategory category) {
        this.category = category;
        return this;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public Task setPriority(TaskPriority priority) {
        this.priority = priority;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Task setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public Task setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(status, task.status) && Objects.equals(category, task.category) && Objects.equals(priority, task.priority) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(startDateTime, task.startDateTime) && Objects.equals(endDateTime, task.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, category, priority, title, description, startDateTime, endDateTime);
    }
}
