# --- !Ups

create table topic(
  id int(10) NOT NULL AUTO_INCREMENT,
  name varchar(100),
  PRIMARY KEY (id)
);

# --- !Downs
drop table topic;
