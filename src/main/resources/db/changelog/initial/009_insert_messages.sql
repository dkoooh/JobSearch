delete from MESSAGES;

insert into MESSAGES (SENDER_ID, RESPONDED_APPLICANT_ID, CONTENT, TIMESTAMP)
values (
        4,
        3,
        'test1',
        current_timestamp()
       ),
       (
           4,
           4,
           'test2',
           current_timestamp()
       );