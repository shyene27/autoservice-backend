DROP TABLE if EXISTS autoservice.users;
CREATE TABLE autoservice.users
(
userId varchar(50) NOT null,
userName varchar(50) NOT null,
userPassword varchar(50) NOT null,
userFirstName varchar(50) DEFAULT null,
userLastName varchar(50) DEFAULT null,
userEmail varchar(50) NOT null,
PRIMARY KEY (userId)
);