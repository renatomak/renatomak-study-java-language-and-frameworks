set foreign_key_checks = 0;

delete from products;
delete from orders;
delete from sales;

set foreign_key_checks = 1;

alter table products auto_increment = 1;
alter table orders auto_increment = 1;
alter table sales auto_increment = 1;

insert into products (id, name, quantity) values (1, 'celular sansung', 10);
insert into products (id, name, quantity) values (2, 'celular Moto G7', 15);
insert into products (id, name, quantity) values (3, 'celular sansung A12', 20);
insert into products (id, name, quantity) values (4, 'celular Moto G9', 5);
insert into products (id, name, quantity) values (5, 'celular POCO M3', 11);

insert into orders (id) values (1), (2), (3);

insert into sales (product_id, order_id, quantity) values (1, 2, 5);
insert into sales (product_id, order_id, quantity) values (2, 2, 3);
insert into sales (product_id, order_id, quantity) values (5, 2, 1);
insert into sales (product_id, order_id, quantity) values (3, 1, 10);
insert into sales (product_id, order_id, quantity) values (4, 1, 3);