-- create table users
-- (
--     id           int primary key not null auto_increment,
--     name         text            not null,
--     surname      text            not null,
--     age          int,
--     email        text unique     not null,
--     password     text            not null,
--     phone_number varchar(55)     not null,
--     avatar       text,
--     account_type varchar(50)     not null
-- );
--
-- create table categories
-- (
--     id        int primary key not null auto_increment,
--     name      text            not null,
--     parent_id int,
--     constraint fk_parent_id foreign key (parent_id) references categories (id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table contact_types
-- (
--     id   int primary key not null auto_increment,
--     type text            not null
-- );
--
-- create table resumes
-- (
--     id           int primary key not null auto_increment,
--     applicant_id int,
--     name         text            not null,
--     category_id  int,
--     salary       real,
--     is_active    boolean,
--     created_date timestamp,
--     update_time  timestamp,
--     constraint fk_applicant_id foreign key (applicant_id) references users (id)
--         on delete restrict
--         on update cascade,
--     constraint fk_category_id_resumes foreign key (category_id) references categories (id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table contact_info
-- (
--     id            int primary key not null auto_increment,
--     type_id       int,
--     resume_id     int,
--     contact_value text            not null,
--     constraint fk_type_id foreign key (type_id) references contact_types (id)
--         on delete restrict
--         on update cascade,
--     constraint fk_resume_id_contact_info foreign key (resume_id) references resumes (id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table education_info
-- (
--     id          int primary key not null auto_increment,
--     resume_id   int             not null,
--     institution text            not null,
--     program     text            not null,
--     start_date  date            not null,
--     end_date    date            not null,
--     degree      text,
--     constraint fk_resume_id_edu foreign key (resume_id) references resumes (id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table vacancies
-- (
--     id           int primary key not null auto_increment,
--     name         text            not null,
--     description  text,
--     category_id  int,
--     salary       real,
--     exp_from     int,
--     exp_to       int,
--     is_active    boolean,
--     author_id    int,
--     created_date timestamp,
--     update_date  timestamp,
--     constraint fk_category_id_vacancies foreign key (category_id) references categories (id)
--         on delete restrict
--         on update cascade,
--     constraint fk_author_id foreign key (author_id) references users (id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table responded_applicants
-- (
--     id           int primary key not null auto_increment,
--     resume_id    int,
--     vacancy_id   int,
--     confirmation boolean,
--     constraint fk_resume_id_resp foreign key (resume_id) references resumes (id)
--         on delete restrict
--         on update cascade,
--     constraint fk_vacancy_id_resp foreign key (vacancy_id) references vacancies (id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table messages
-- (
--     id                     int primary key not null auto_increment,
--     responded_applicant_id int             not null,
--     content                text,
--     timestamp              timestamp,
--     constraint fk_responded_applicant_id foreign key (responded_applicant_id) references responded_applicants (id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table work_experience_info
-- (
--     id               int primary key not null auto_increment,
--     resume_id        int             not null,
--     years            int             not null,
--     company_name     text            not null,
--     position         text            not null,
--     responsibilities text            not null,
--     constraint fk_resume_id_work_exp foreign key (resume_id) references resumes (id)
--         on delete restrict
--         on update cascade
-- );

insert into users (name, surname, age, email, password, phone_number, account_type)
values ('John', 'Doe', 30, 'jonhdoe@gmail.com', '123', '0700707700', 'applicant'),
       ('Bill', 'Milner', 45, 'bill.milner@gmail.com', '321', '0505500500', 'employer');

insert into categories (name)
values ('Web');

insert into categories (name, parent_id)
values ('Back-end', (select id from categories where name = 'Web')),
       ('Front-end', (select id from categories where name = 'Web'));

insert into vacancies (name, category_id, author_id)
values ('Middle Front-end developer',
        (select id from categories where name = 'Front-end'),
        (select id from users where email = 'bill.milner@gmail.com')),
       ('Middle Java developer',
        (select id from categories where name = 'Back-end'),
        (select id from users where email = 'bill.milner@gmail.com'));

insert into resumes (name, applicant_id, category_id)
values ('Java developer',
        (select id from users where email = 'jonhdoe@gmail.com'),
        (select id from categories where name = 'Back-end')),
       ('Front-end developer',
        (select id from users where email = 'jonhdoe@gmail.com'),
        (select id from categories where name = 'Front-end'));

insert into responded_applicants (resume_id, vacancy_id, confirmation)
values ((select id from resumes where name = 'Front-end developer'),
        (select id from vacancies where name = 'Middle Front-end developer'),
        true),
       ((select id from resumes where name = 'Java developer'),
        (select id from vacancies where name = 'Middle Java developer'),
        false);

insert into contact_types (type)
values ('Telegram'),
       ('Email'),
       ('Number'),
       ('Facebook'),
       ('LinkedIn');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id
         from contact_types
         where type = 'Telegram'),
        (select id
         from resumes
         where name = 'Java developer'),
        '@johndoe'),
       ((select id
         from contact_types
         where type = 'Telegram'),
        (select id
         from resumes
         where name = 'Front-end developer'),
        '@johndoe');


insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Front-end developer'),
        'Attractor School',
        'JS developer',
        '2022-01-02',
        '2023-04-10',
        'Junior JS developer'),
       ((select id from resumes where name = 'Front-end developer'),
        'Kyrgyz State Technical University',
        'Applied Informatics',
        '2018-09-02',
        '2023-06-10',
        'Bachelor of Applied Informatics and Software Engineering'),
       ((select id from resumes where name = 'Java developer'),
        'Attractor School',
        'Java developer',
        '2020-09-10',
        '2021-08-20',
        'Junior Java developer (Spring)'),
       ((select id from resumes where name = 'Java developer'),
        'Kyrgyz State Technical University',
        'Applied Informatics',
        '2018-09-02',
        '2023-06-10',
        'Bachelor of Applied Informatics and Software Engineering');

insert into messages (responded_applicant_id, content)
values ((select id
         from responded_applicants
         where resume_id = (
             select id from resumes where name = 'Front-end developer'
             )),
        'test1'),
       ((select id
         from responded_applicants
         where resume_id = (
             select id from resumes where name = 'Java developer'
             )),
        'test2');

insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values (
        (select id from resumes where name = 'Front-end developer'),
        2,
        'DevCats',
        'Middle Frontend-Developer',
        'Программирование и проектирование; Оптимизация веб-приложения; Участие в код-ревью'
       ),
       (
        (select id from resumes where name = 'Java developer'),
        1,
        'Lol-rofl company',
        'Junior Java dev',
        'some responsibilities'
        );

