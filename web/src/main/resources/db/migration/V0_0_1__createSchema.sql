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
    wuser_id int references wuser(id) on delete cascade
);

create sequence workout_id_seq start with 1 increment by 1;