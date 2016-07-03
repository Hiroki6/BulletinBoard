# --- !Ups

create table post_comment(
  id int(10) NOT NULL AUTO_INCREMENT,
  content varchar(255) NOT NULL,
  post_id int(10) NOT NULL,
  PRIMARY KEy(id)
);

# --- !Downs
drop table post_comment;
