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

create table bank_accounts
(
    id                bigint default nextval('clever_bank_accounts_id_seq'::regclass)      not null
        constraint bank_accounts_pk
            primary key,
    balance           numeric                                                              not null,
    currency          money                                                                not null,
    account_open_date date                                                                 not null,
    user_id           bigint default nextval('clever_bank_accounts_user_id_seq'::regclass) not null
        constraint bank_accounts_users_id_fk
            references users
            on update cascade on delete cascade,
    bank_id           bigint                                                               not null
        constraint bank_accounts_banks_id_fk
            references banks
            on update cascade on delete cascade
);

create table transactionals
(
    id                        bigserial
        constraint transactionals_pk
            primary key,
    date                      date        not null,
    currency                  money       not null,
    monies                    numeric     not null,
    operation_type            varchar(40) not null,
    sending_bank_id           bigint
        constraint transactionals_banks_id_fk
            references banks
            on update cascade on delete cascade,
    sending_bank_account_id   bigint      not null
        constraint transactionals_bank_accounts_id_fk
            references bank_accounts,
    repitient_bank_id         bigint      not null
        constraint transactionals_banks_id_fk2
            references banks
            on update cascade on delete cascade,
    repitient_bank_account_id bigint      not null
        constraint transactionals_bank_accounts_id_fk2
            references bank_accounts
            on update cascade on delete cascade
);