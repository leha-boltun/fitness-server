create table wuser_prog (
    wuser_id int not null references wuser(id),
    prog_id bigint not null references prog(id),
    primary key (wuser_id, prog_id)
)