#create easymall database
create database easymall;
#change to database easymall
use easymall;
#create table user
create table user(
  id int primary key auto_increment,
  username varchar(50),
  password varchar(50),
  nickname varchar(50),
  email varchar(50)
);
insert into user values(null, 'admin', '123', '��������Ա', 'admin@163.com');
insert into user values(null, '�ŷ�', '123', '����Ա', 'zhangfei@163.com');create database easymall;
#add column to user because of shiro
alter table user add column role varchar(10) NULL after email;
update user set role='user' where id <=11;
update user set role='admin' where id >11;