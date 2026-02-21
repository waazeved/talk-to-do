package com.waltsoft.talk_to_do.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "CREATED_AT",
            insertable = false,
            updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "UPDATED_AT",
            insertable = false,
            updatable = false)
    protected LocalDateTime updatedAt;

    public BasicEntity() {
    }

    public BasicEntity(BasicEntity other) {
        id = other.id;
        createdAt = other.createdAt;
        updatedAt = other.updatedAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BasicEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt);
    }
}