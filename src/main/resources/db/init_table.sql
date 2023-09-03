drop table if exists banks cascade;

create table banks
(
    id   bigserial
        constraint banks_pk
            primary key,
    name varchar(200) not null
);

drop table if exists users cascade;

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

drop table if exists currencies cascade;

create table currencies
(
    id   bigserial
        constraint currencies_pk
            primary key,
    code varchar(10) not null
);

drop table if exists clever_bank_accounts cascade;

create table clever_bank_accounts
(
    id                bigserial
        constraint clever_bank_accounts_pk
            primary key,
    balance           numeric not null,
    account_open_date date    not null,
    date_of_last_service date,
    user_id           bigserial
        constraint clever_bank_accounts_users_id_fk
            references users
            on update cascade on delete cascade,
    number            bigint  not null,
    currency_id       bigint  not null
        constraint clever_bank_accounts_currencies_id_fk
            references currencies
            on update cascade on delete cascade
);

drop table if exists beta_bank_accounts cascade;

create table beta_bank_accounts
(
    id                bigserial
        constraint beta_bank_accounts_pk
            primary key,
    balance           numeric not null,
    account_open_date date    not null,
    user_id           bigserial
        constraint beta_bank_accounts_users_id_fk
            references users
            on update cascade on delete cascade,
    number            bigint  not null,
    currency_id       bigint  not null
        constraint beta_bank_accounts_currencies_id_fk
            references currencies
            on update cascade on delete cascade
);

drop table if exists gamma_bank_accounts cascade;

create table gamma_bank_accounts
(
    id                bigserial
        constraint gamma_bank_accounts_pk
            primary key,
    balance           numeric not null,
    account_open_date date    not null,
    user_id           bigserial
        constraint gamma_bank_accounts_users_id_fk
            references users
            on update cascade on delete cascade,
    number            bigint  not null,
    currency_id       bigint  not null
        constraint gamma_bank_accounts_currencies_id_fk
            references currencies
            on update cascade on delete cascade
);

drop table if exists zeta_bank_accounts cascade;

create table zeta_bank_accounts
(
    id                bigserial
        constraint zeta_bank_accounts_pk
            primary key,
    balance           numeric not null,
    account_open_date date    not null,
    user_id           bigserial
        constraint zeta_bank_accounts_users_id_fk
            references users
            on update cascade on delete cascade,
    number            bigint  not null,
    currency_id       bigint  not null
        constraint zeta_bank_accounts_currencies_id_fk
            references currencies
            on update cascade on delete cascade
);

drop table if exists sigma_bank_accounts cascade;

create table sigma_bank_accounts
(
    id                bigserial
        constraint sigma_bank_accounts_pk
            primary key,
    balance           numeric not null,
    account_open_date date    not null,
    user_id           bigserial
        constraint sigma_bank_accounts_users_id_fk
            references users
            on update cascade on delete cascade,
    number            bigint  not null,
    currency_id       bigint  not null
        constraint sigma_bank_accounts_currencies_id_fk
            references currencies
            on update cascade on delete cascade
);

drop table if exists operation_types cascade;

create table operation_types
(
    id   bigserial
        constraint operation_types_pk
            primary key,
    type varchar(20) not null
);


drop table if exists transactions cascade;

create table transactions
(
    id                        bigserial
        constraint transactions_pk
            primary key,
    date                      date    not null,
    monies                    numeric not null,
    sending_bank_id           bigserial
        constraint transactions_banks_id_fk
            references banks
            on update cascade on delete cascade,
    sending_bank_account_id   bigserial,
    recipient_bank_id         bigserial
        constraint transactions_banks_id_fk2
            references banks
            on update cascade on delete cascade,
    recipient_bank_account_id bigserial,
    currency_id               bigint  not null
        constraint transactions_currencies_id_fk
            references currencies
            on update cascade on delete cascade,
    operation_type_id         bigserial
        constraint transactions_operation_types_id_fk
            references operation_types
            on update cascade on delete cascade
);
