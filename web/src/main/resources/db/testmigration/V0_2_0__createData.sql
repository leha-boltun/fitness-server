insert into wuser
values (nextval('wuser_id_seq'), 1, 'user 1'),
       (nextval('wuser_id_seq'), 1, 'user 2');

insert into exer
values (nextval('exer_id_seq'), 1, 'Exercise 1'),
       (nextval('exer_id_seq'), 1, 'Exercise 2'),
       (nextval('exer_id_seq'), 1, 'Exercise 3');

insert into prog
values (nextval('prog_id_seq'), 1, 'Program 1'),
       (nextval('prog_id_seq'), 1, 'Program 2');

insert into prog_exer
values ((select id from prog where name = 'Program 1'),
        (select id from exer where name = 'Exercise 1'),
        1, 1),
       ((select id from prog where name = 'Program 1'),
        (select id from exer where name = 'Exercise 2'),
        1, 2),
       ((select id from prog where name = 'Program 2'),
        (select id from exer where name = 'Exercise 1'),
        1, 1),
       ((select id from prog where name = 'Program 2'),
        (select id from exer where name = 'Exercise 3'),
        1, 2);

insert into workout
values (nextval('workout_id_seq'), 1, now() - interval '2 days', true, 1, 1),
       (nextval('workout_id_seq'), 1, now() - interval '5 days', true, 2, 1),
       (nextval('workout_id_seq'), 1, now() - interval '6 days', true, 2, 2);

insert into event_type
values (nextval('event_type_id_seq'), 1, 'Переоделся', 100, ''),
       (nextval('event_type_id_seq'), 1, 'Размялся', 200, 'BEGIN'),
       (nextval('event_type_id_seq'), 1, 'Закончил', 300, 'END');
