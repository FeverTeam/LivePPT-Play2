# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table attender (
  id                        bigint auto_increment not null,
  meeting_id                bigint not null,
  user_id                   bigint not null,
  constraint pk_attender primary key (id))
;

create table meeting (
  id                        bigint auto_increment not null,
  ppt_id                    bigint,
  user_id                   bigint,
  topic                     varchar(255) not null,
  current_page_index        bigint not null,
  constraint pk_meeting primary key (id))
;

create table ppt (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  title                     varchar(255) not null,
  time                      datetime not null,
  store_key                 varchar(255) not null,
  file_size                 bigint not null,
  is_converted              tinyint(1) default 0 not null,
  pagecount                 integer not null,
  constraint pk_ppt primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  displayname               varchar(255),
  constraint pk_user primary key (id))
;

alter table attender add constraint fk_attender_meeting_1 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;
create index ix_attender_meeting_1 on attender (meeting_id);
alter table attender add constraint fk_attender_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_attender_user_2 on attender (user_id);
alter table meeting add constraint fk_meeting_ppt_3 foreign key (ppt_id) references ppt (id) on delete restrict on update restrict;
create index ix_meeting_ppt_3 on meeting (ppt_id);
alter table meeting add constraint fk_meeting_founder_4 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_meeting_founder_4 on meeting (user_id);
alter table ppt add constraint fk_ppt_owner_5 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_ppt_owner_5 on ppt (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table attender;

drop table meeting;

drop table ppt;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

