insert into users (name, surname, age, email, password, phone_number, account_type)
values ('John', 'Doe', 30, 'jonhdoe@gmail.com', '123', '0700707700', 'applicant'),
       ('Bill', 'Milner', 45, 'bill.milner@gmail.com', '321', '0505500500', 'employer');

insert into categories (name)
values ('Web');

insert into categories (name, parent_id)
values ('Back-end', (select id from categories where name = 'Web')),
       ('Front-end', (select id from categories where name = 'Web'));

insert into vacancies (name, category_id, author_id, CREATED_DATE, UPDATE_TIME)
values ('Middle Front-end developer',
        (select id from categories where name = 'Front-end'),
        (select id from users where email = 'bill.milner@gmail.com'),
        '2024-02-02 15:00',
        '2024-02-02 15:00'),
       ('Middle Java developer',
        (select id from categories where name = 'Back-end'),
        (select id from users where email = 'bill.milner@gmail.com'),
        '2024-02-02 15:00',
        '2024-02-02 15:00');

insert into resumes (name, applicant_id, category_id, CREATED_DATE, UPDATE_TIME)
values ('Java developer',
        (select id from users where email = 'jonhdoe@gmail.com'),
        (select id from categories where name = 'Back-end'),
        '2024-02-02 15:00',
        '2024-02-02 15:00'),
       ('Front-end developer',
        (select id from users where email = 'jonhdoe@gmail.com'),
        (select id from categories where name = 'Front-end'),
        '2024-02-02 15:00',
        '2024-02-02 15:00');

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

