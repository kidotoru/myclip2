

drop table if exists access_history cascade;
drop table if exists collect_article cascade;
drop table if exists collect_info cascade;
drop table if exists article cascade;
drop table if exists collect_site cascade;


create table access_history (
  id INT comment 'id'
  , to_article_id INT not null comment 'リダイレクト先記事ID'
  , from_ip VARCHAR(32) comment 'アクセス元IP'
  , created_at TIMESTAMP comment '生成日時'
  , updated_at TIMESTAMP comment '更新日時'
  , constraint access_history_PKC primary key (id)
) comment 'アクセス履歴' ;

-- 収集サイト

create table collect_site (
  id INT comment 'id'
  , url VARCHAR(512) comment 'URL'
  , site_name VARCHAR(512) comment 'サイト名'
  , flg_delete CHAR(1) default '0' not null comment '削除フラグ'
  , last_pub_at TIMESTAMP comment '最終発行日時'
  , created_at TIMESTAMP comment '生成日時'
  , updated_at TIMESTAMP comment '更新日時'
  , constraint collect_site_PKC primary key (id)
) comment '収集サイト' ;

-- 収集情報

create table collect_info (
  id INT comment 'id'
  , created_at TIMESTAMP comment '生成日時'
  , updated_at TIMESTAMP comment '更新日時'
  , constraint collect_info_PKC primary key (id)
) comment '収集情報' ;

-- 収集記事

create table collect_article (
  id INT comment 'id'
  , collect_id INT not null comment '収集ID'
  , article_id INT not null comment '記事ID'
  , created_at TIMESTAMP comment '生成日時'
  , updated_at TIMESTAMP comment '更新日時'
  , constraint collect_article_PKC primary key (id)
) comment '収集記事' ;

-- 記事

create table article (
  id INT comment 'id'
  , site_id INT not null comment 'サイトID'
  , article_url VARCHAR(512) comment '記事URL'
  , article_title VARCHAR(512) comment '記事名'
  , article_contents BLOB comment '記事内容'
  , publish_at TIMESTAMP comment '配信日時'
  , created_at TIMESTAMP comment '生成日時'
  , updated_at TIMESTAMP comment '更新日時'
  , constraint article_PKC primary key (id)
) comment '記事' ;

alter table access_history
  add constraint access_history_FK1 foreign key (to_article_id) references article(id);

alter table article
  add constraint article_FK1 foreign key (site_id) references collect_site(id);

alter table collect_article
  add constraint collect_article_FK1 foreign key (article_id) references article(id);

alter table collect_article
  add constraint collect_article_FK2 foreign key (collect_id) references collect_info(id);

