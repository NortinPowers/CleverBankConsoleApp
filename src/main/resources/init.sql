create table banks
(
    id   bigserial
        constraint banks_pk
            primary key,
    name varchar(200) not null
);

create table users
(
    id           bigserial
        constraint users_pk
            primary key,
    "passportId" varchar(40) not null
        constraint users_pk2
            unique,
    login        varchar(80) not null,
    password     varchar(80) not null,
    name         varchar(80) not null,
    surname      varchar(80) not null,
    patronymic   varchar(80)
);

create table currency
(
    id   bigint default nextval('"Currency_id_seq"'::regclass) not null
        constraint currency_pk
            primary key,
    code varchar(10)                                           not null
);

create table bank_accounts
(
    id                bigserial
        constraint bank_accounts_pk
            primary key,
    balance           numeric not null,
    account_open_date date    not null,
    user_id           bigserial
        constraint bank_accounts_users_id_fk
            references users
            on update cascade on delete cascade,
    bank_id           bigserial
        constraint bank_accounts_banks_id_fk
            references banks
            on update cascade on delete cascade,
    number            bigint  not null,
    currency_id       bigint  not null
        constraint bank_accounts_currency_id_fk
            references currency
            on update cascade on delete cascade
);

create table transactions
(
    id                        bigserial
        constraint transactions_pk
            primary key,
    date                      date        not null,
    monies                    numeric     not null,
    operation_type            varchar(40) not null,
    sending_bank_id           bigserial
        constraint transactions_banks_id_fk
            references banks
            on update cascade on delete cascade,
    sending_bank_account_id   bigserial
        constraint transactions_bank_accounts_id_fk
            references bank_accounts
            on update cascade on delete cascade,
    recipient_bank_id         bigserial
        constraint transactions_banks_id_fk2
            references banks
            on update cascade on delete cascade,
    recipient_bank_account_id bigserial
        constraint transactions_bank_accounts_id_fk2
            references bank_accounts
            on update cascade on delete cascade,
    currency_id               bigint      not null
        constraint transactions_currency_id_fk
            references currency
            on update cascade on delete cascade
);