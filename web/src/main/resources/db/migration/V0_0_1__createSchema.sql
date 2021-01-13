create table wuser (
    id int not null primary key,
    version int not null,
    name text not null
);

create sequence wuser_id_seq start with 1 increment by 1;

create table exer (
    id bigint not null primary key,
    version int not null,
    name text not null
);

create sequence exer_id_seq start with 1 increment by 1;

create table prog (
    id bigint not null primary key,
    version int not null,
    name text not null
);

create sequence prog_id_seq start with 1 increment by 1;

create table prog_exer (
    prog_id bigint not null references prog(id) on delete cascade,
    exer_id bigint not null references exer(id) on delete cascade,
    version int not null,
    exer_order int not null,
    primary key (prog_id, exer_id)
);

create table workout (
    id bigint not null primary key,
    version int not null,
    wdate date not null,
    finished bool not null,
    prog_id bigint not null references prog(id) on delete cascade,
    wuser_id int not null references wuser(id) on delete cascade
);

create sequence workout_id_seq start with 1 increment by 1;

create table workout_exer (
    id bigint not null primary key,
    version int not null,
    workout_id bigint not null references workout(id) on delete cascade,
    exer_id bigint not null references exer(id) on delete cascade,
    exer_order int not null
);

create sequence workout_exer_id_seq start with 1 increment by 1;

create table wset (
    id bigint not null primary key,
    version int not null,
    weight text not null,
    count text not null,
    wset_order int not null,
    workout_exer_id bigint not null references workout_exer(id) on delete cascade
);

create sequence wset_id_seq start with 1 increment by 1;

create table event_type (
    id int not null primary key,
    version int not null,
    name text unique not null,
    event_order int not null,
    event_code text not null
);

create sequence event_type_id_seq start with 1 increment by 1;

create table time_stamp (
    id bigint not null primary key,
    version int not null,
    wtime time not null,
    workout_id bigint not null references workout(id) on delete cascade,
    event_type_id int not null references event_type(id) on delete cascade
);

create sequence time_stamp_id_seq start with 1 increment by 1;