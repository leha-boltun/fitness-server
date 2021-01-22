insert into wuser_prog
values ((select id from wuser where name = 'user 1'), (select id from prog where name = 'Program 1')),
       ((select id from wuser where name = 'user 1'), (select id from prog where name = 'Program 2')),
       ((select id from wuser where name = 'user 2'), (select id from prog where name = 'Program 2'));