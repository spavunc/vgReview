CREATE TABLE IF NOT EXISTS Game(
game_id long AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30),
api_id int,
profile_picture varchar(50),
description clob,
score int,
video_id VARCHAR(255)
);

create table if not exists User(
user_id long auto_increment primary key,
username varchar(30) unique,
creation_date date,
email varchar(50) unique,
password varchar(250),
profile_picture varchar(50)
);

CREATE TABLE IF NOT EXISTS Genre(
genre_id long AUTO_INCREMENT PRIMARY KEY,
name varchar(30)
);

CREATE TABLE IF NOT EXISTS Rating(
rating_id long auto_increment primary key,
score int,
user_id long not null,
game_id long not null,
foreign key (user_id) references User(user_id),
foreign key  (game_id) references Game(game_id)
);

create table if not exists Review(
review_id long auto_increment primary key,
review text,
user_id long not null,
game_id long not null,
foreign key (user_id) references User(user_id),
foreign key  (game_id) references Game(game_id)
);

create table if not exists Game_Genre(
game_id long not null,
genre_id long not null,
foreign key(game_id) references Game(game_id),
foreign key(genre_id) references Genre(genre_id)
);

create table if not exists Screenshot(
screenshot_id long not null auto_increment,
api_id long not null,
image_id long not null,
game_id long not null,
foreign key  (game_id) references Game(game_id)
);

CREATE TABLE IF NOT EXISTS Authority(
authority_id INT primary key,
name VARCHAR(100) not null
);

CREATE TABLE IF NOT EXISTS User_Authority(
user_id bigint not null,
authority_id bigint not null,
foreign key (user_id) references User(user_id),
foreign key (authority_id) references Authority(authority_id)
);

insert into Authority(authority_id, name)
values(1, 'ROLE_ADMIN');

insert into Authority(authority_id, name)
values(2, 'ROLE_USER');

insert into User(user_id, username, password, creation_date, email)
values(1, 'admin', '$2a$12$Pxv18Nk46UXXiqzFEEPUW.T3y7GTzs0CIxuPUJ.nHK4SIBfHAhgTK', sysdate, 'admin@admin.com');

insert into User(user_id, username, password, creation_date, email)
values(2, 'user', '$2a$12$M2GYr24ev1Rd7H91/co4nOMBSjoY3YN5JvCPk2dEZ8PVwxc9IToF2', sysdate, 'user@user.com');

insert into User_Authority(user_id, authority_id)
values(1, 1);

insert into User_Authority(user_id, authority_id)
values(2, 2);


