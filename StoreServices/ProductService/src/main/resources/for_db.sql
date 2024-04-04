create sequence product_seq
    maxvalue 1000;

alter sequence product_seq owner to postgres;

create sequence product_1seq
    maxvalue 1000;

alter sequence product_1seq owner to postgres;

create sequence users_seq1;

alter sequence users_seq1 owner to postgres;

create table product
(
    id    integer          not null
        constraint product_pk
            primary key,
    name  varchar          not null,
    price double precision not null
);

alter table product
    owner to postgres;

alter sequence product_seq owned by product.id;

create table users
(
    id       integer not null
        constraint users_pk
            primary key,
    login    varchar not null,
    password varchar not null
);

alter table users
    owner to postgres;

create table "order"
(
    id          integer not null
        constraint order_pk
            primary key,
    user_id     integer not null
        constraint order_pk_2
            unique
        constraint order_users_id_fk
            references users,
    product_id  integer not null
        constraint order_product_id_fk
            references product,
    adding_time time    not null
);

alter table "order"
    owner to postgres;

create table inventory
(
    id         integer not null
        constraint inventory_pk
            primary key,
    product_id integer not null
        constraint inventory_product_id_fk
            references product,
    count      bigint  not null
);

alter table inventory
    owner to postgres;

