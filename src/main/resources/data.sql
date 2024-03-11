create table users
(
    id int primary key not null auto_increment,
    name text not null,
    surname text not null,
    age int,
    email text unique not null,
    password text not null,
    phone_number varchar(55) not null,
    avatar text,
    account_type varchar (50) not null
);

create table categories
(
    id int primary key not null auto_increment,
    name text not null,
    parent_id int,
    constraint fk_parent_id foreign key (parent_id) references categories (id)
        on delete restrict
        on update cascade
);

create table contact_types
(
    id int primary key not null auto_increment,
    type text not null
);

create table resumes
(
    id int primary key not null auto_increment,
    applicant_id int,
    name text not null,
    category_id int,
    salary real,
    is_active boolean,
    created_date timestamp,
    update_time timestamp,
    constraint fk_applicant_id foreign key (applicant_id) references users (id)
        on delete restrict
        on update cascade,
    constraint fk_category_id_resumes foreign key (category_id) references categories (id)
        on delete restrict
        on update cascade
);

create table contact_info
(
    id int primary key not null auto_increment,
    type_id int,
    resume_id int,
    contact_value text not null,
    constraint fk_type_id foreign key (type_id) references contact_types (id)
        on delete restrict
        on update cascade,
    constraint fk_resume_id_contact_info foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade
);

create table education_info
(
    id int primary key not null auto_increment,
    resume_id int not null,
    institution text not null,
    program text not null,
    start_date date not null,
    end_date date not null,
    degree text,
    constraint fk_resume_id_edu foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade
);

create table vacancies
(
    id int primary key not null auto_increment,
    name text not null,
    description text,
    category_id int,
    salary real,
    exp_from int,
    exp_to int,
    is_active boolean,
    author_id int,
    created_date timestamp,
    update_date timestamp,
    constraint fk_category_id_vacancies foreign key (category_id) references categories (id)
        on delete restrict
        on update cascade,
    constraint fk_author_id foreign key (author_id) references users (id)
        on delete restrict
        on update cascade
);

create table responded_applicants
(
    id int primary key not null auto_increment,
    resume_id int,
    vacancy_id int,
    confirmation boolean,
    constraint fk_resume_id_resp foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade,
    constraint fk_vacancy_id_resp foreign key (vacancy_id) references vacancies (id)
        on delete restrict
        on update cascade
);

create table messages
(
    id int primary key not null auto_increment,
    responded_applicant_id int not null,
    content text,
    timestamp timestamp,
    constraint fk_responded_applicant_id foreign key (responded_applicant_id) references responded_applicants (id)
        on delete restrict
        on update cascade
);

create table work_experience_info
(
    id int primary key not null auto_increment,
    resume_id int not null ,
    years int not null ,
    company_name text not null,
    position text not null ,
    responsibilities text not null ,
    constraint fk_resume_id_work_exp foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade
);