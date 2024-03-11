--changeset
create table messages
(
    id        uuid primary key,
    content   text,
    created_at timestamp
);
--rollback
