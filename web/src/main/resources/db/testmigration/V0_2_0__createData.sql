insert into wuser values
                         (nextval('wuser_id_seq'), 1, 'user 1'),
                         (nextval('wuser_id_seq'), 1, 'user 2');

insert into workout values
                            (nextval('workout_id_seq'), 1, now() - interval '2 days', true, 1),
                            (nextval('workout_id_seq'), 1, now(), true, 1),
                            (nextval('workout_id_seq'), 1, now(), true, 2)