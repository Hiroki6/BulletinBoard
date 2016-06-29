# --- !Ups
create table topic(
  id int(10) not null auto_increment,
  name varchar(100),
  primary key(id)
);

# --- !Downs
drop table topic;