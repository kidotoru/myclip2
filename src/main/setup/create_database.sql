
-- データベースを作る
create database myclip2 character set utf8;

-- ユーザを作る
grant all privileges on myclip2.* to myclip2_admin@localhost identified by 'myclip2_admin' with grant option;
flush privileges;

-- ユーザを作る
grant all privileges on myclip2.* to myclip2@localhost identified by 'myclip2';
flush privileges;