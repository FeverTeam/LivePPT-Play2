# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups


create table ppt (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  file_name                 varchar(255) not null,
  time                      datetime not null,
  file_size                 bigint not null,
  is_converted              tinyint(1) default 0 not null,
  pagecount                 integer not null,
  constraint pk_ppt primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  display                   varchar(255),
  constraint pk_user primary key (id))
;

alter table ppt add constraint fk_ppt_owner_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_ppt_owner_1 on ppt (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table ppt;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

