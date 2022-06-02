create table if not exists user
(
    id        int auto_increment
        primary key,
    birthdate datetime     not null,
    email     varchar(255) not null,
    firstname varchar(255) not null,
    lastname  varchar(255) not null,
    password  varchar(255) not null
)
    engine = MyISAM;

