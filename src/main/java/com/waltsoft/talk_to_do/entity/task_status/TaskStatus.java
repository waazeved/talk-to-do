package com.waltsoft.talk_to_do.entity.task_status;


import com.waltsoft.talk_to_do.entity.BasicEntity;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = TaskStatus.TABLE_NAME)
public class TaskStatus extends BasicEntity {

    public static final String TABLE_NAME = "TASK_STATUS";

    private static final String CODE_COLUMN = "CODE";

    private static final long serialVersionUID = -1216620611213906687L;

    @Column(name = CODE_COLUMN)
    @Enumerated(EnumType.STRING)
    private TaskStatusCodeEnum code;

    public TaskStatus() {
    }

    public TaskStatus(TaskStatusCodeEnum code) {
        this.code = code;
    }

    public TaskStatusCodeEnum getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TaskStatus that)) return false;
        if (!super.equals(o)) return false;
        return code==that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code);
    }
}
