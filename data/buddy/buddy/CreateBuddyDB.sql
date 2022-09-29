CREATE DATABASE IF NOT EXISTS buddy;

use buddy;

create table if not exists buddy.payments
(
    id int auto_increment,
    message varchar(255) not null,
    transaction_date datetime not null,
    value int not null,
    receiver_id int not null,
    sender_id int not null,
    constraint `PRIMARY` primary key (id)
)
    engine=MyISAM;

create index FK_payment_sender
    on buddy.payments (sender_id);

create index FK_payment_receiver
    on buddy.payments (receiver_id);

create table if not exists buddy.users
(
    id int auto_increment,
    birthdate date not null,
    email varchar(255) not null,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    password varchar(255) not null,
    wallet_id int null,
    constraint `PRIMARY` primary key (id)
)
    engine=MyISAM;

create index FK_user_wallet
    on buddy.users (wallet_id);

create table if not exists buddy.users_contacts
(
    user_id int not null,
    contacts_id int not null,
    constraint `PRIMARY`
        primary key (user_id, contacts_id)
)
    engine=MyISAM;

create index FK_users_contacts
    on buddy.users_contacts (contacts_id);

create table if not exists buddy.wallets
(
    id int auto_increment,
    balance int not null,
    user_id int not null,
    constraint `PRIMARY` primary key (id)
)
    engine=MyISAM;

create index FK_wallet_user
    on buddy.wallets (user_id);

create table if not exists buddy.wallets_payments
(
    wallet_id int not null,
    payments_id int not null,
    constraint `PRIMARY`
        primary key (wallet_id, payments_id)
)
    engine=MyISAM;

create index FK_wallet_payments
    on buddy.wallets_payments (payments_id);
