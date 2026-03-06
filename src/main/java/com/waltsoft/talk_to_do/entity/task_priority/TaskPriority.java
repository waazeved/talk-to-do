package com.waltsoft.talk_to_do.entity.task_priority;


import com.waltsoft.talk_to_do.entity.BasicEntity;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = TaskPriority.TABLE_NAME)
public class TaskPriority extends BasicEntity {

    public static final String TABLE_NAME = "TASK_PRIORITY";

    private static final String CODE_COLUMN = "CODE";

    private static final long serialVersionUID = 7901881776224499966L;

    @Column(name = CODE_COLUMN)
    @Enumerated(EnumType.STRING)
    private TaskPriorityCodeEnum code;

    public TaskPriority() {
    }

    public TaskPriority(TaskPriorityCodeEnum code) {
        this.code = code;
    }

    public TaskPriorityCodeEnum getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TaskPriority that)) return false;
        if (!super.equals(o)) return false;
        return getCode()==that.getCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCode());
    }
}
