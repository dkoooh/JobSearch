insert into USERS (name, surname, age, email, password, phone_number, account_type, enabled)
values ('Alice', 'Johnson', 28, 'alice.johnson@example.com',
        '$2a$12$N7/Z5b0oWysJ0iW2Nj/Ee.mwvTJuMlStjmo9j3c6ZazQWCSdGcrQ6', '0711111111', 'applicant', true),
       ('Bob', 'Smith', 35, 'bob.smith@example.com', '$2a$12$JFX2M0eqO4iqJsKYLkCwQex5MvivT3BfiSPhvj3j2Kftt9hG06eDm',
        '0722222222', 'employer', true),
       ('Emma', 'Brown', 40, 'emma.brown@example.com', '$2a$12$CwXf/uZaA3Tc.7D9IitvHulqPysoeAlQzNsfn7A8CYFnFGNV8ylHW',
        '0733333333', 'employer', true),
       ('Grace', 'Wilson', 32, 'grace.wilson@example.com',
        '$2a$12$ZRrMoSJ4AZ12BBl8yIPFJehLz2uVLsUX3ANcc8rDcn27AbXNwu2Zu', '0744444444', 'applicant', true),
       ('David', 'Lee', 50, 'david.lee@example.com', '$2a$12$27mbpAsiRVH.n2bHOpMqUe4pHUuYYJUHhe3Ufgsoj/TZqJfMoye0S',
        '0755555555', 'applicant', true),
       ('Emily', 'Johnson', 30, 'emily.johnson@example.com',
        '$2a$12$z7Y9j.Ve.Gq0vHdF7BdyGupj.wRnRpsTXue1/VuX4W5lBybVq0Nvy', '0777777777', 'employer', true),
       ('Michael', 'Williams', 38, 'michael.williams@example.com',
        '$2a$12$R/PPIJG0QMcSsbvhGqLk9Ohz9uXc8PM5MF6ZvFJ0Hxh5C3obWw9g6', '0788888888', 'applicant', true),
       ('Sophia', 'Brown', 25, 'sophia.brown@example.com',
        '$2a$12$uLrxrN3jfrU6S02L6O6X9Ond4NR1HmLzbDqIvV/VtJ7LRNrFtGhzC', '0799999999', 'applicant', true),
       ('Matthew', 'Jones', 33, 'matthew.jones@example.com',
        '$2a$12$.5LcrFkCEf94MfKIs8IqWuW20R2NLKyRJbGK4HLxSMNr2jyX7.PXa', '0800000000', 'employer', true);

insert into USER_AUTHORITY (user_id, authority_id)
values ((select id from users where email = 'alice.johnson@example.com'),
        (select id from authorities where authority = 'APPLICANT')),
       ((select id from users where email = 'bob.smith@example.com'),
        (select id from authorities where authority = 'EMPLOYER')),
       ((select id from users where email = 'emma.brown@example.com'),
        (select id from authorities where authority = 'EMPLOYER')),
       ((select id from users where email = 'grace.wilson@example.com'),
        (select id from authorities where authority = 'APPLICANT')),
       ((select id from users where email = 'david.lee@example.com'),
        (select id from authorities where authority = 'APPLICANT')),
       ((select id from users where email = 'emily.johnson@example.com'),
        (select id from authorities where authority = 'EMPLOYER')),
       ((select id from users where email = 'michael.williams@example.com'),
        (select id from authorities where authority = 'APPLICANT')),
       ((select id from users where email = 'sophia.brown@example.com'),
        (select id from authorities where authority = 'APPLICANT')),
       ((select id from users where email = 'matthew.jones@example.com'),
        (select id from authorities where authority = 'EMPLOYER'));

insert into categories (name)
values ('SMM'),
       ('Web-designer'),
       ('Photographer'),
       ('Receptionist'),
       ('Software Engineer');

insert into vacancies (name, category_id, author_id, CREATED_DATE, UPDATE_TIME, description, salary, is_Active,
                       exp_From, exp_To)
values ('Photographer', (select id from categories where name = 'Photographer'),
        (select id from users where email = 'bob.smith@example.com'), '2024-05-25 09:00', '2024-06-25 09:00',
        'We are looking for a skilled photographer to join our team.', 60000.00, true, 3, 5),
       ('SMM Specialist', (select id from categories where name = 'SMM'),
        (select id from users where email = 'bob.smith@example.com'), '2024-05-26 10:00', '2024-06-26 10:00',
        'Seeking a talented SMM specialist to handle social media accounts.', 7500.00, true, 2, 4),
       ('UI/UX Designer', (select id from categories where name = 'Web-designer'),
        (select id from users where email = 'emma.brown@example.com'), '2024-05-29 13:00', '2024-06-29 13:00',
        'Looking for a creative UI/UX Designer to enhance user experience.', 55000.00, true, 3, 5),
       ('SEO Specialist', (select id from categories where name = 'SMM'),
        (select id from users where email = 'emily.johnson@example.com'), '2024-05-30 14:00', '2024-06-30 14:00',
        'Seeking an experienced SEO Specialist to improve website ranking.', 90000.00, true, 2, 4),
       ('Digital Marketing Manager', (select id from categories where name = 'SMM'),
        (select id from users where email = 'bob.smith@example.com'), '2024-05-31 15:00', '2024-06-30 15:00',
        'Hiring a Digital Marketing Manager to lead our marketing efforts.', 85000.00, true, 4, 7),
       ('Graphic Designer', (select id from categories where name = 'Web-designer'),
        (select id from users where email = 'matthew.jones@example.com'), '2024-06-01 16:00', '2024-07-01 16:00',
        'We are seeking a talented Graphic Designer to create stunning visuals.', 72200.00, true, 2, 4),
       ('Content Writer', (select id from categories where name = 'SMM'),
        (select id from users where email = 'emily.johnson@example.com'), '2024-06-02 17:00', '2024-07-02 17:00',
        'Looking for a skilled Content Writer to produce engaging content.', 51900.00, true, 2, 3),
       ('Customer Support Specialist', (select id from categories where name = 'Receptionist'),
        (select id from users where email = 'emma.brown@example.com'), '2024-06-03 18:00', '2024-07-03 18:00',
        'Join our team as a Customer Support Specialist and provide excellent service.', 32100.00, true, 1, 3),
       ('Data Analyst', (select id from categories where name = 'SMM'),
        (select id from users where email = 'matthew.jones@example.com'), '2024-06-04 19:00', '2024-07-04 19:00',
        'Seeking a Data Analyst to analyze and interpret data for insights.', 90000.00, true, 3, 5),
       ('Sales Manager', (select id from categories where name = 'Receptionist'),
        (select id from users where email = 'bob.smith@example.com'), '2024-06-05 20:00', '2024-07-05 20:00',
        'Hiring a Sales Manager to lead our sales team and achieve targets.', 88000.00, true, 4, 6),
       ('HR Specialist', (select id from categories where name = 'Receptionist'),
        (select id from users where email = 'david.lee@example.com'), '2024-06-06 21:00', '2024-07-06 21:00',
        'We are looking for an HR Specialist to handle recruitment processes.', 66600.00, true, 2, 4),
       ('Network Administrator', (select id from categories where name = 'Web-designer'),
        (select id from users where email = 'alice.johnson@example.com'), '2024-06-07 22:00', '2024-07-07 22:00',
        'Join us as a Network Administrator and manage our network infrastructure.', 33000.00, true, 4, 6),
       ('Financial Analyst', (select id from categories where name = 'SMM'),
        (select id from users where email = 'emma.brown@example.com'), '2024-06-08 23:00', '2024-07-08 23:00',
        'Seeking a Financial Analyst to provide insights into financial data.', 52700.00, true, 3, 5);



insert into resumes (name, applicant_id, category_id, CREATED_DATE, UPDATE_TIME, salary, is_Active)
values ('Android Developer', (select id from users where email = 'alice.johnson@example.com'),
        (select id from categories where name = 'Software Engineer'), '2024-03-20 13:00', '2024-03-20 13:00', 70000.0,
        true),
       ('Middle Java Backend Engineer', (select id from users where email = 'grace.wilson@example.com'),
        (select id from categories where name = 'Back-end'), '2024-03-25 14:00', '2024-03-25 14:00', 75000.0, true),
       ('Angular Frontend Developer', (select id from users where email = 'grace.wilson@example.com'),
        (select id from categories where name = 'Front-end'), '2024-03-30 15:00', '2024-03-30 15:00', 70000.0, true),
       ('iOS Developer', (select id from users where email = 'alice.johnson@example.com'),
        (select id from categories where name = 'Software Engineer'), '2024-04-11 10:00', '2024-04-11 10:00', 80000.0,
        true),
       ('Python Developer', (select id from users where email = 'bob.smith@example.com'),
        (select id from categories where name = 'Back-end'), '2024-04-12 11:00', '2024-04-12 11:00', 75000.0, true),
       ('Vue.js Frontend Developer', (select id from users where email = 'emma.brown@example.com'),
        (select id from categories where name = 'Front-end'), '2024-04-13 12:00', '2024-04-13 12:00', 75000.0, true),
       ('Node.js Backend Developer', (select id from users where email = 'grace.wilson@example.com'),
        (select id from categories where name = 'Back-end'), '2024-04-14 13:00', '2024-04-14 13:00', 80000.0, true),
       ('React Native Developer', (select id from users where email = 'david.lee@example.com'),
        (select id from categories where name = 'Software Engineer'), '2024-04-15 14:00', '2024-04-15 14:00', 75000.0,
        true),
       ('Java Spring Developer', (select id from users where email = 'alice.johnson@example.com'),
        (select id from categories where name = 'Back-end'), '2024-04-16 15:00', '2024-04-16 15:00', 80000.0, true),
       ('Full Stack Developer', (select id from users where email = 'emma.brown@example.com'),
        (select id from categories where name = 'Web'), '2024-04-18 17:00', '2024-04-18 17:00', 80000.0, true),
       ('UI/UX Designer', (select id from users where email = 'grace.wilson@example.com'),
        (select id from categories where name = 'Front-end'), '2024-04-19 18:00', '2024-04-19 18:00', 70000.0, true),
       ('Software Engineer', (select id from users where email = 'david.lee@example.com'),
        (select id from categories where name = 'Back-end'), '2024-04-20 19:00', '2024-04-20 19:00', 85000.0, true),
       ('SMM Specialist', (select id from users where email = 'john.doe@example.com'),
        (select id from categories where name = 'SMM'), '2024-04-21 20:00', '2024-04-21 20:00', 50000.0, true),
       ('Photographer', (select id from users where email = 'michael.williams@example.com'),
        (select id from categories where name = 'Photographer'), '2024-04-22 21:00', '2024-04-22 21:00', 60000.0, true),
       ('Receptionist', (select id from users where email = 'sophia.brown@example.com'),
        (select id from categories where name = 'Receptionist'), '2024-04-23 22:00', '2024-04-23 22:00', 45000.0, true);


insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Java Spring Developer'), 3, 'Meta', 'Java Spring Developer',
        'Describe typical java back-end responsibilities');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Android Developer'), 2, 'XYZ Mobile Solutions', 'Android Developer',
        'Developed and maintained Android applications with a focus on UI/UX design and performance optimization.'),
       ((select id from resumes where name = 'Android Developer'), 1, 'Tech Innovators', 'Mobile App Developer',
        'Collaborated with cross-functional teams to develop innovative mobile solutions, including integration with backend services.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Middle Java Backend Engineer'), 3, 'ABC Software',
        'Java Backend Engineer',
        'Designed and implemented RESTful APIs for high-traffic web applications using Java and Spring framework.'),
       ((select id from resumes where name = 'Middle Java Backend Engineer'), 2, 'Tech Solutions Ltd.',
        'Software Developer',
        'Participated in full software development lifecycle, from requirement analysis to deployment, focusing on Java-based backend systems.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Angular Frontend Developer'), 2, 'WebTech Innovations',
        'Angular Developer',
        'Developed responsive and interactive web applications using Angular framework and TypeScript.'),
       ((select id from resumes where name = 'Angular Frontend Developer'), 1, 'Creative Web Solutions',
        'Frontend Developer',
        'Implemented pixel-perfect designs into web applications, ensuring cross-browser compatibility and performance optimization.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'iOS Developer'), 3, 'Tech Giants Inc.', 'iOS Developer',
        'Led iOS app development projects from concept to release, collaborating with designers and backend engineers for seamless integration and user experience optimization.'),
       ((select id from resumes where name = 'iOS Developer'), 2, 'Mobile Innovations Co.', 'Mobile App Developer',
        'Implemented new features and enhancements for iOS applications, ensuring adherence to Apple design guidelines and best practices.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Python Developer'), 4, 'Data Analytics Solutions', 'Python Developer',
        'Developed data processing pipelines and machine learning models using Python and libraries like Pandas, NumPy, and Scikit-learn.'),
       ((select id from resumes where name = 'Python Developer'), 2, 'AI Innovations Ltd.', 'Software Engineer',
        'Contributed to the development of AI-driven applications, focusing on natural language processing and computer vision.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Vue.js Frontend Developer'), 3, 'WebDesign Experts', 'Vue.js Developer',
        'Built responsive and dynamic user interfaces using Vue.js framework, integrating with backend APIs for data retrieval and manipulation.'),
       ((select id from resumes where name = 'Vue.js Frontend Developer'), 2, 'UI/UX Solutions Inc.',
        'Frontend Engineer',
        'Worked closely with UI/UX designers to implement visually appealing and intuitive interfaces for web applications.');

insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Node.js Backend Developer'), 3, 'Cloud Services Co.',
        'Node.js Backend Developer',
        'Developed scalable and efficient server-side applications using Node.js, Express, and MongoDB, ensuring high performance and reliability.'),
       ((select id from resumes where name = 'Node.js Backend Developer'), 2, 'Tech Solutions Ltd.',
        'Software Engineer',
        'Contributed to the development of RESTful APIs and microservices architecture, implementing authentication and authorization mechanisms.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'React Native Developer'), 3, 'App Development Agency',
        'React Native Developer',
        'Built cross-platform mobile applications using React Native, ensuring native-like performance and user experience on both iOS and Android platforms.'),
       ((select id from resumes where name = 'React Native Developer'), 2, 'Tech Innovations Co.',
        'Mobile App Engineer',
        'Worked on improving the performance and stability of existing React Native applications, optimizing codebase and integrating new features.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Java Spring Developer'), 3, 'Meta', 'Java Spring Developer',
        'Designed and implemented scalable microservices architecture using Java Spring Boot, integrating with various third-party services and APIs.'),
       ((select id from resumes where name = 'Java Spring Developer'), 2, 'Tech Innovations Ltd.', 'Backend Developer',
        'Developed and maintained Java Spring-based backend systems, including RESTful APIs and business logic implementation.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Full Stack Developer'), 4, 'Web Solutions Co.', 'Full Stack Developer',
        'Led the development of full-stack web applications from concept to deployment, utilizing technologies such as Node.js, React, and MongoDB.'),
       ((select id from resumes where name = 'Full Stack Developer'), 2, 'Tech Innovations Ltd.', 'Software Engineer',
        'Contributed to frontend and backend development tasks, ensuring seamless integration and optimal performance of web applications.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'UI/UX Designer'), 3, 'Design Agency Inc.', 'UI/UX Designer',
        'Designed intuitive and visually appealing user interfaces for web and mobile applications, focusing on user experience and interaction design principles.'),
       ((select id from resumes where name = 'UI/UX Designer'), 2, 'Tech Solutions Ltd.', 'Product Designer',
        'Collaborated with product managers and developers to conceptualize and prototype new features, iterating designs based on user feedback.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Software Engineer'), 5, 'Tech Innovations Ltd.', 'Software Engineer',
        'Led software development projects, from initial planning and architecture design to implementation, testing, and deployment.'),
       ((select id from resumes where name = 'Software Engineer'), 3, 'Software Solutions Co.', 'Senior Developer',
        'Designed and developed scalable and maintainable software solutions, leveraging best practices and emerging technologies.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'SMM Specialist'), 3, 'Social Media Agency', 'SMM Specialist',
        'Developed and implemented social media marketing strategies to increase brand awareness, engagement, and conversion rates across multiple platforms.'),
       ((select id from resumes where name = 'SMM Specialist'), 2, 'Digital Marketing Solutions',
        'Social Media Manager',
        'Managed social media accounts, creating and curating content, analyzing performance metrics, and optimizing campaigns for maximum impact.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Photographer'), 4, 'Freelance Photographer', 'Photographer',
        'Captured and edited high-quality photographs for various clients, including portraits, events, and commercial projects.'),
       ((select id from resumes where name = 'Photographer'), 2, 'Creative Studios', 'Photography Assistant',
        'Assisted lead photographers with setup, lighting, and post-processing tasks, ensuring smooth workflow and timely delivery of projects.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id from resumes where name = 'Receptionist'), 3, 'Corporate Office Ltd.', 'Receptionist',
        'Managed front desk operations, including greeting visitors, answering calls, and coordinating appointments and meetings.'),
       ((select id from resumes where name = 'Receptionist'), 2, 'Hospitality Services Co.', 'Front Desk Assistant',
        'Provided administrative support, including handling incoming mail, scheduling reservations, and assisting guests with inquiries.');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Android Developer'), 'Stanford University', 'Mobile App Development',
        '2016-09-1', '2020-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Android Developer'), 'MIT', 'Advanced Android Development', '2016-09-1',
        '2020-06-01', 'MSc in Mobile Technologies');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Middle Java Backend Engineer'), 'Harvard University', 'Computer Science',
        '2015-09-01', '2019-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Middle Java Backend Engineer'), 'Stanford University',
        'Software Engineering', '2019-09-01', '2021-06-01', 'MSc in Software Engineering');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Angular Frontend Developer'), 'University of California, Berkeley',
        'Web Development', '2017-09-01', '2021-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Angular Frontend Developer'), 'MIT', 'User Experience Design',
        '2021-09-01', '2023-06-01', 'MSc in Human-Computer Interaction');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'iOS Developer'), 'Stanford University', 'Mobile App Development',
        '2015-09-01', '2019-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'iOS Developer'), 'Harvard University', 'iOS Development', '2019-09-01',
        '2021-06-01', 'MSc in Mobile Technologies');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Python Developer'), 'Massachusetts Institute of Technology',
        'Computer Science', '2014-09-01', '2018-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Python Developer'), 'Stanford University', 'Machine Learning',
        '2018-09-01', '2020-06-01', 'MSc in Artificial Intelligence');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Vue.js Frontend Developer'), 'University of California, Los Angeles',
        'Computer Science', '2016-09-01', '2020-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Vue.js Frontend Developer'), 'MIT', 'Frontend Development', '2020-09-01',
        '2022-06-01', 'MSc in Web Technologies');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Node.js Backend Developer'), 'Harvard University', 'Computer Science',
        '2015-09-01', '2019-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Node.js Backend Developer'), 'Stanford University',
        'Software Engineering', '2019-09-01', '2021-06-01', 'MSc in Software Engineering');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'React Native Developer'), 'University of California, Berkeley',
        'Computer Science', '2016-09-01', '2020-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'React Native Developer'), 'MIT', 'Mobile App Development', '2020-09-01',
        '2022-06-01', 'MSc in Mobile Technologies');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Java Spring Developer'), 'Stanford University', 'Computer Science',
        '2015-09-01', '2019-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Java Spring Developer'), 'Harvard University', 'Software Engineering',
        '2019-09-01', '2021-06-01', 'MSc in Software Engineering');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Full Stack Developer'), 'Massachusetts Institute of Technology',
        'Computer Science', '2014-09-01', '2018-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Full Stack Developer'), 'Stanford University', 'Software Engineering',
        '2018-09-01', '2020-06-01', 'MSc in Software Engineering');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'UI/UX Designer'), 'California Institute of the Arts', 'Graphic Design',
        '2016-09-01', '2020-06-01', 'BFA in Graphic Design'),
       ((select id from resumes where name = 'UI/UX Designer'), 'New York University', 'Interactive Media Arts',
        '2020-09-01', '2022-06-01', 'MFA in Interactive Design');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Software Engineer'), 'Massachusetts Institute of Technology',
        'Computer Science', '2014-09-01', '2018-06-01', 'BSc in Computer Science'),
       ((select id from resumes where name = 'Software Engineer'), 'Stanford University', 'Software Engineering',
        '2018-09-01', '2020-06-01', 'MSc in Software Engineering');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'SMM Specialist'), 'University of California, Los Angeles', 'Marketing',
        '2015-09-01', '2019-06-01', 'BSc in Marketing'),
       ((select id from resumes where name = 'SMM Specialist'), 'New York University', 'Digital Marketing',
        '2019-09-01', '2021-06-01', 'MSc in Digital Marketing');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Photographer'), 'Brooks Institute', 'Photography', '2013-09-01',
        '2017-06-01', 'BFA in Photography'),
       ((select id from resumes where name = 'Photographer'), 'California College of the Arts', 'Visual Studies',
        '2017-09-01', '2019-06-01', 'MFA in Visual Arts');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id from resumes where name = 'Receptionist'), 'University of California, Berkeley',
        'Hospitality Management', '2016-09-01', '2020-06-01', 'BSc in Hospitality Management'),
       ((select id from resumes where name = 'Receptionist'), 'New York University', 'Administrative Studies',
        '2020-09-01', '2022-06-01', 'MSc in Business Administration');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Android Developer'), '@android_dev_alice'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'Android Developer'),
        'android.dev@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'Android Developer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Android Developer'), 'facebook.com/android.dev.alice'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Android Developer'), 'linkedin.com/in/android-dev-alice');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Middle Java Backend Engineer'), '@middlejava'),
       ((select id from contact_types where type = 'Email'),
        (select id from resumes where name = 'Middle Java Backend Engineer'), 'middle.java@example.com'),
       ((select id from contact_types where type = 'Number'),
        (select id from resumes where name = 'Middle Java Backend Engineer'), '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Middle Java Backend Engineer'), 'facebook.com/middle-java'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Middle Java Backend Engineer'), 'linkedin.com/in/middle-java');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Angular Frontend Developer'), '@angular_guru'),
       ((select id from contact_types where type = 'Email'),
        (select id from resumes where name = 'Angular Frontend Developer'), 'angular.guru@example.com'),
       ((select id from contact_types where type = 'Number'),
        (select id from resumes where name = 'Angular Frontend Developer'), '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Angular Frontend Developer'), 'facebook.com/angular.guru'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Angular Frontend Developer'), 'linkedin.com/in/angular-guru');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'), (select id from resumes where name = 'iOS Developer'),
        '@iosdevbob'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'iOS Developer'),
        'ios.dev.bob@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'iOS Developer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'), (select id from resumes where name = 'iOS Developer'),
        'facebook.com/ios.dev.bob'),
       ((select id from contact_types where type = 'LinkedIn'), (select id from resumes where name = 'iOS Developer'),
        'linkedin.com/in/ios-dev-bob');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Python Developer'), '@pythonista_emma'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'Python Developer'),
        'pythonista.emma@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'Python Developer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Python Developer'), 'facebook.com/pythonista.emma'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Python Developer'), 'linkedin.com/in/pythonista-emma');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Vue.js Frontend Developer'), '@vuejsdev'),
       ((select id from contact_types where type = 'Email'),
        (select id from resumes where name = 'Vue.js Frontend Developer'), 'vuejs.dev@example.com'),
       ((select id from contact_types where type = 'Number'),
        (select id from resumes where name = 'Vue.js Frontend Developer'), '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Vue.js Frontend Developer'), 'facebook.com/vuejs.dev'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Vue.js Frontend Developer'), 'linkedin.com/in/vuejs-dev');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Node.js Backend Developer'), '@nodejsdev'),
       ((select id from contact_types where type = 'Email'),
        (select id from resumes where name = 'Node.js Backend Developer'), 'nodejs.dev@example.com'),
       ((select id from contact_types where type = 'Number'),
        (select id from resumes where name = 'Node.js Backend Developer'), '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Node.js Backend Developer'), 'facebook.com/nodejs.dev'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Node.js Backend Developer'), 'linkedin.com/in/nodejs-dev');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'React Native Developer'), '@reactnativedev'),
       ((select id from contact_types where type = 'Email'),
        (select id from resumes where name = 'React Native Developer'), 'reactnative.dev@example.com'),
       ((select id from contact_types where type = 'Number'),
        (select id from resumes where name = 'React Native Developer'), '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'React Native Developer'), 'facebook.com/reactnative.dev'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'React Native Developer'), 'linkedin.com/in/reactnative-dev');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Java Spring Developer'), '@alicejohnson'),
       ((select id from contact_types where type = 'Email'),
        (select id from resumes where name = 'Java Spring Developer'), 'alice.johnson@example.com'),
       ((select id from contact_types where type = 'Number'),
        (select id from resumes where name = 'Java Spring Developer'), '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Java Spring Developer'), 'facebook.com/alice.johnson'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Java Spring Developer'), 'linkedin.com/in/alice-johnson');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Full Stack Developer'), '@fullstack_dev'),
       ((select id from contact_types where type = 'Email'),
        (select id from resumes where name = 'Full Stack Developer'), 'fullstack.dev@example.com'),
       ((select id from contact_types where type = 'Number'),
        (select id from resumes where name = 'Full Stack Developer'), '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Full Stack Developer'), 'facebook.com/fullstack.dev'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Full Stack Developer'), 'linkedin.com/in/fullstack-dev');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'), (select id from resumes where name = 'UI/UX Designer'),
        '@uidesigner_emma'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'UI/UX Designer'),
        'ui.designer.emma@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'UI/UX Designer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'), (select id from resumes where name = 'UI/UX Designer'),
        'facebook.com/ui.designer.emma'),
       ((select id from contact_types where type = 'LinkedIn'), (select id from resumes where name = 'UI/UX Designer'),
        'linkedin.com/in/ui-designer-emma');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'),
        (select id from resumes where name = 'Software Engineer'), '@davidlee'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'Software Engineer'),
        'david.lee@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'Software Engineer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'),
        (select id from resumes where name = 'Software Engineer'), 'facebook.com/david.lee'),
       ((select id from contact_types where type = 'LinkedIn'),
        (select id from resumes where name = 'Software Engineer'), 'linkedin.com/in/david-lee');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'), (select id from resumes where name = 'SMM Specialist'),
        '@john_doe'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'SMM Specialist'),
        'john.doe@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'SMM Specialist'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'), (select id from resumes where name = 'SMM Specialist'),
        'facebook.com/john.doe'),
       ((select id from contact_types where type = 'LinkedIn'), (select id from resumes where name = 'SMM Specialist'),
        'linkedin.com/in/john-doe');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'), (select id from resumes where name = 'Photographer'),
        '@michaelwilliams'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'Photographer'),
        'michael.williams@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'Photographer'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'), (select id from resumes where name = 'Photographer'),
        'facebook.com/michael.williams'),
       ((select id from contact_types where type = 'LinkedIn'), (select id from resumes where name = 'Photographer'),
        'linkedin.com/in/michael-williams');

insert into contacts_info (type_id, resume_id, contact_value)
values ((select id from contact_types where type = 'Telegram'), (select id from resumes where name = 'Receptionist'),
        '@sophiabrown'),
       ((select id from contact_types where type = 'Email'), (select id from resumes where name = 'Receptionist'),
        'sophia.brown@example.com'),
       ((select id from contact_types where type = 'Number'), (select id from resumes where name = 'Receptionist'),
        '+1234567890'),
       ((select id from contact_types where type = 'Facebook'), (select id from resumes where name = 'Receptionist'),
        'facebook.com/sophia.brown'),
       ((select id from contact_types where type = 'LinkedIn'), (select id from resumes where name = 'Receptionist'),
        'linkedin.com/in/sophia-brown');
