--liquibase formatted sql

--changeset sunil:1
create table if not exists book
(
    id varchar(500) not null
        constraint "bookPK"
        primary key,
    title varchar(500) not null
        constraint tenant_tenant_id_key
        unique
);
