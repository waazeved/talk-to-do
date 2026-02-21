--liquibase formatted sql
--changeset walter.azvdo:seed-task-category-status-priority-tables context:seed splitStatements:true endDelimiter:;

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
       ('LOW')

INSERT INTO task_category (code)
VALUES ('WORK'),
       ('PERSONAL'),
       ('HOUSE'),
       ('HEALTH'),
       ('FITNESS'),
       ('EDUCATION'),
       ('FINANCE'),
       ('FAMILY'),
       ('SHOPPING'),
       ('SOCIAL'),
       ('HOBBY'),
       ('TRAVEL'),
       ('MEALS'),
       ('APPOINTMENTS'),
       ('KIDS'),
       ('PETS'),
       ('MAINTENANCE'),
       ('SELF_CARE'),
       ('CHORES'),
       ('OTHERS');

--rollback truncate TABLE task_status
--rollback truncate TABLE task_priority
--rollback truncate TABLE task_category