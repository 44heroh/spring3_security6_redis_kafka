INSERT INTO courses.course (id, author, name) VALUES (1, 'Петров А.В', '1 Основы кройки и шитья');
INSERT INTO courses.course (id, author, name) VALUES (2, 'Мошкина А.В', '2 Введение в архитектуру дизайна');
INSERT INTO courses.course (id, author, name) VALUES (3, 'Смирнов А.А', 'Основы ООП');

create table course
(
    id     bigint generated by default as identity (maxvalue 2147483647)
        primary key,
    author text,
    name   varchar(200) not null
);

alter table course
    owner to demo;