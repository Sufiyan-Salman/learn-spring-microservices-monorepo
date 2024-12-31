create table if not exists inventory (
                      id integer not null,
                      product_code varchar(255),
                      quantity integer,
                      primary key (id)
                      );
insert into inventory(id,product_code,quantity)
values(20001, 'inventory1', 20);

insert into inventory(id,product_code,quantity)
values(20002, 'inventory2', 10);

insert into inventory(id,product_code,quantity)
values(20003, 'inventory3', 12);
