create table if not exists user_contacts
(
    user_id     int not null,
    contacts_id int not null
)
    engine = MyISAM;

create index FK_user_contacts_contact_id
    on user_contacts (contacts_id);

create index FK_user_contacts_user_id
    on user_contacts (user_id);

