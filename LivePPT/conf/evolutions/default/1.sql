# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ppt (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  title                     varchar(255),
  time                      datetime,
  store_key                 varchar(255),
  file_size                 bigint,
  is_converted              tinyint(1) default 0,
  constraint pk_ppt primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  displayname               varchar(255),
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table ppt;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

