# --- !Ups

create table post(
  id int(10) NOT NULL AUTO_INCREMENT,
  content text NOT NULL,
  topic_id int(10) NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs
drop table post;
