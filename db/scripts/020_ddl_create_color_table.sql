create table color(
id serial primary key,
name varchar not null
);

insert into color (name) values ('Black');
insert into color (name) values ('White');
insert into color (name) values ('Red');
insert into color (name) values ('Blue');
insert into color (name) values ('Brown');

ALTER TABLE cars ADD COLUMN color_id int not null REFERENCES color(id)






