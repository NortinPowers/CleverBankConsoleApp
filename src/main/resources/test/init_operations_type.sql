create table operation_types
(
    id   bigserial
        constraint operation_types_pk
            primary key,
    type varchar(20) not null
);


