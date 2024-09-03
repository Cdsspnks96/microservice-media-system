create database subscriptions;
grant all privileges on subscriptions.* to 'todo'@'%' identified by 'todosecret';

create database trending;
grant all privileges on trending.* to 'todo'@'%' identified by 'todosecret';