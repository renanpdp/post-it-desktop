select * from PostIt;
select * from User;

delete from PostIt where id>=1;
delete from User where id>=1;

insert into User (id, username, password) values (1,'renan','1234');
insert into User (id, username, password) values (2,'lucas','1234');

insert into PostIt (userId, color, text, dateCreated) values (1,'Green','Teste 1','2014-07-01');
insert into PostIt (userId, color, text, dateCreated) values (1,'Purple','Teste 2','2014-07-01');
insert into PostIt (userId, color, text, dateCreated) values (1,'Pink','Teste 3','2014-07-01');
insert into PostIt (userId, color, text, dateCreated) values (2,'Pink','Teste 4','2014-07-01');
insert into PostIt (userId, color, text, dateCreated) values (2,'Purple','Teste 5','2014-07-01');
insert into PostIt (userId, color, text, dateCreated) values (2,'Pink','Teste 6','2014-07-01');
insert into PostIt (userId, color, text, dateCreated) values (2,'Green','Teste 7','2014-07-01');