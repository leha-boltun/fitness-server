alter table prog add column prev_prog_id int references prog(id) on delete cascade;
alter table prog add column is_previous boolean not null default false;