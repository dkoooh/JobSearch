insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'Java developer'),
        'john.doe@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'Java developer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'), (select id from resumes where name = 'Java developer'),
        'john.doe'),
       ((select id from contact_types where type = 'LinkedIn'), (select id from resumes where name = 'Java developer'),
        'john-doe');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'Front-end developer'),
        'john.doe@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'Front-end developer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'), (select id from resumes where name = 'Front-end developer'),
        'john.doe'),
       ((select id from contact_types where type = 'LinkedIn'), (select id from resumes where name = 'Front-end developer'),
        'john-doe');