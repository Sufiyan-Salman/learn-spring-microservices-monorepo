create table if not exists orders (
                      id integer not null,
                      user_id integer not null,
                      inventory_id integer not null,
                      product_code varchar(255),
                      quantity integer,
                      primary key (id)
                      );
--insert into order(id,user_id,inventory_id,product_code,quantity)
--values(30001, 10001, 20001, 'inventory1', 20);
--
--insert into order(id,product_code,quantity)
--values(30002, 10002, 20002, 'inventory2', 10);
--
--insert into order(id,product_code,quantity)
--values(30003, 10003, 20003, 'inventory3', 12);
