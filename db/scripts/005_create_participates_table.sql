create table participates (
   id serial primary key,
   post_id int not null references auto_post(id),
   user_id int not null references auto_user(id),
   unique (post_id, user_id)
);