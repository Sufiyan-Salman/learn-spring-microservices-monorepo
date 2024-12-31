create table if not exists users (
                      id integer not null,
                      name varchar(255),
                      email varchar(255),
                      primary key (id)
                      );
insert into users(id,name,email)
values(10001, 'user1', 'email1');

insert into users(id,name,email)
values(10002, 'user2', 'email2');

insert into users(id,name,email)
values(10003, 'user3', 'email3');
