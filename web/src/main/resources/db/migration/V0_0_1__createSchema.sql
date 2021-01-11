create table wuser (
    id int not null primary key,
    version int not null,
    name text not null
);

create sequence wuser_id_seq start with 1 increment by 1;

create table workout (
    id bigint not null primary key,
    version int not null,
    wdate date not null,
    finished bool not null,
    wuser_id int references wuser(id) on delete cascade
);

create sequence workout_id_seq start with 1 increment by 1;

create table event_type (
    id int not null primary key,
    version int not null,
    name text unique not null,
    event_order int not null
);

create sequence event_type_id_seq start with 1 increment by 1;

create table time_stamp (
    id bigint not null primary key,
    version int not null,
    wtime time not null,
    workout_id bigint references workout(id) on delete cascade,
    event_type_id int references event_type(id) on delete cascade
);

create sequence time_stamp_id_seq start with 1 increment by 1;