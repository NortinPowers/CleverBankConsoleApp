create table clever_bank_accounts
(
    id                bigserial
        constraint clever_bank_accounts_pk
            primary key,
    balance           numeric not null,
    account_open_date date    not null,
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