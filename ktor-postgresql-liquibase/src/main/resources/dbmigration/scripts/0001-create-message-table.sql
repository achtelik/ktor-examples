--changeset
create table messages
(
    id        uuid primary key,
    content   text,
    createdAt timestamp
);
--rollback
