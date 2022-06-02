create table if not exists payment
(
    id               int auto_increment
        primary key,
    message          varchar(255) not null,
    transaction_date datetime     not null,
    receiver_id      int          not null,
    sender_id        int          not null
)
    engine = MyISAM;

create index FK_payment_receiver_id
    on payment (receiver_id);

create index FK_payment_sender_id
    on payment (sender_id);

