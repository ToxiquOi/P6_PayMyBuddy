create table if not exists buddy.payments
(
	id int auto_increment
		constraint `PRIMARY`
			primary key,
	message varchar(255) not null,
	transaction_date datetime not null,
	value int not null,
	receiver_id int not null,
	sender_id int not null
)
engine=MyISAM;

create index FK82fbwl5ub3jcexrpitlo9ma1d
	on buddy.payments (sender_id);

create index FKcdypnkk0lf3e7hmnp4a9l1ut4
	on buddy.payments (receiver_id);

create table if not exists buddy.users
(
	id int auto_increment
		constraint `PRIMARY`
			primary key,
	birthdate date not null,
	email varchar(255) not null,
	firstname varchar(255) not null,
	lastname varchar(255) not null,
	password varchar(255) not null,
	wallet_id int null
)
engine=MyISAM;

create index FKcf6cgic6n7ek155uj81npsbcm
	on buddy.users (wallet_id);

create table if not exists buddy.users_contacts
(
	user_id int not null,
	contacts_id int not null,
	constraint `PRIMARY`
		primary key (user_id, contacts_id)
)
engine=MyISAM;

create index FKk3tm0hl8g666sund07dr6epgo
	on buddy.users_contacts (contacts_id);

create table if not exists buddy.wallets
(
	id int auto_increment
		constraint `PRIMARY`primary key,
	balance int not null,
	user_id int not null
)
engine=MyISAM;

create index FKc1foyisidw7wqqrkamafuwn4e
	on buddy.wallets (user_id);

create table if not exists buddy.wallets_payments
(
	wallet_id int not null,
	payments_id int not null,
	constraint `PRIMARY`
		primary key (wallet_id, payments_id)
)
engine=MyISAM;

create index FKs7lkg4bx2ac2y5tf9uuwx1538
	on buddy.wallets_payments (payments_id);


