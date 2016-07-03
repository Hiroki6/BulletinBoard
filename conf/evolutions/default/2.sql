# --- !Ups

create table topic_post(
  id int(10) NOT NULL AUTO_INCREMENT,
  content varchar(255) NOT NULL,
  topic_id int(10) NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs
drop table topic_post;
