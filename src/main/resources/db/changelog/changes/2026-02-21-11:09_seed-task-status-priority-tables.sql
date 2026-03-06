--liquibase formatted sql
--changeset walter.azvdo:seed-task-status-priority-tables context:"seed,seedTest" splitStatements:true endDelimiter:;

INSERT INTO task_status (code)
VALUES ('PENDING'),
       ('IN_PROGRESS'),
       ('ON_HOLD'),
       ('COMPLETED'),
       ('CANCELLED');

INSERT INTO task_priority (code)
VALUES ('URGENT'),
       ('HIGH'),
       ('MEDIUM'),
       ('LOW');

--rollback truncate TABLE task_status
--rollback truncate TABLE task_priority