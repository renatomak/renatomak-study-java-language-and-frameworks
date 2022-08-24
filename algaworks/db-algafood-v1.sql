create table estado (
	id bigint not null auto_increment,
    nome varchar(60) not null,
   
    primary key (id)
) engine=InnoDB default charset=utf8;

 select distinct nome_estado from cidade;

drop database algafood;
create database algafood;