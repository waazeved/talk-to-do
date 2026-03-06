package com.waltsoft.talk_to_do.entity.task_category;


import com.waltsoft.talk_to_do.entity.BasicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = TaskCategory.TABLE_NAME)
public class TaskCategory extends BasicEntity {

    public static final String TABLE_NAME = "TASK_CATEGORY";

    private static final String CODE_COLUMN = "CODE";

    private static final long serialVersionUID = -4002968630802321465L;

    @Column(name = CODE_COLUMN)
    private String code;

    public TaskCategory() {
    }

    public TaskCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public TaskCategory setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TaskCategory that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code);
    }
}
