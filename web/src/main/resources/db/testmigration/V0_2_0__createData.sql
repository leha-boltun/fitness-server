insert into wuser values
                         (nextval('wuser_id_seq'), 1, 'user 1'),
                         (nextval('wuser_id_seq'), 1, 'user 2');

insert into workout values
                            (nextval('workout_id_seq'), 1, now() - interval '2 days', true, 1),
                            (nextval('workout_id_seq'), 1, now() - interval '5 days', true, 1),
                            (nextval('workout_id_seq'), 1, now() - interval '6 days', true, 2);

insert into event_type values
                              (nextval('event_type_id_seq'), 1, 'Переоделся', 100),
                              (nextval('event_type_id_seq'), 1, 'Размялся', 200),
                              (nextval('event_type_id_seq'), 1, 'Закончил', 300);
