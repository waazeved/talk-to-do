--liquibase formatted sql
--changeset walter.azvdo:seed-task-category-table context:seed splitStatements:true endDelimiter:;


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

--rollback truncate TABLE task_category