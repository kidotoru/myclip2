-- Project Name : myclip2
-- Date/Time    : 2014/03/27 20:35:41
-- Author       : kido
-- RDBMS Type   : MySQL
-- Application  : A5:SQL Mk-2

-- �A�N�Z�X����
drop table if exists access_history cascade;

create table access_history (
  id INT comment 'id'
  , to_article_id INT not null comment '���_�C���N�g��L��ID'
  , from_ip VARCHAR(32) comment '�A�N�Z�X��IP'
  , created_at TIMESTAMP comment '��������'
  , updated_at TIMESTAMP comment '�X�V����'
  , constraint access_history_PKC primary key (id)
) comment '�A�N�Z�X����' ;

-- ���W�T�C�g
drop table if exists collect_site cascade;

create table collect_site (
  id INT comment 'id'
  , url VARCHAR(512) comment 'URL'
  , site_name VARCHAR(512) comment '�T�C�g��'
  , flg_delete CHAR(1) default '0' not null comment '�폜�t���O'
  , last_pub_at TIMESTAMP comment '�ŏI���s����'
  , created_at TIMESTAMP comment '��������'
  , updated_at TIMESTAMP comment '�X�V����'
  , constraint collect_site_PKC primary key (id)
) comment '���W�T�C�g' ;

-- ���W���
drop table if exists collect_info cascade;

create table collect_info (
  id INT comment 'id'
  , created_at TIMESTAMP comment '��������'
  , updated_at TIMESTAMP comment '�X�V����'
  , constraint collect_info_PKC primary key (id)
) comment '���W���' ;

-- ���W�L��
drop table if exists collect_article cascade;

create table collect_article (
  id INT comment 'id'
  , correct_id INT not null comment '���WID'
  , ariticle_id INT not null comment '�L��ID'
  , created_at TIMESTAMP comment '��������'
  , updated_at TIMESTAMP comment '�X�V����'
  , constraint collect_article_PKC primary key (id)
) comment '���W�L��' ;

-- �L��
drop table if exists article cascade;

create table article (
  id INT comment 'id'
  , site_id INT not null comment '�T�C�gID'
  , article_url VARCHAR(512) comment '�L��URL'
  , article_title VARCHAR(512) comment '�L����'
  , article_contents BLOB comment '�L�����e'
  , publish_at TIMESTAMP comment '�z�M����'
  , created_at TIMESTAMP comment '��������'
  , updated_at TIMESTAMP comment '�X�V����'
  , constraint article_PKC primary key (id)
) comment '�L��' ;

alter table access_history
  add constraint access_history_FK1 foreign key (to_article_id) references article(id);

alter table article
  add constraint article_FK1 foreign key (site_id) references collect_site(id);

alter table collect_article
  add constraint collect_article_FK1 foreign key (ariticle_id) references article(id);

alter table collect_article
  add constraint collect_article_FK2 foreign key (correct_id) references collect_info(id);

