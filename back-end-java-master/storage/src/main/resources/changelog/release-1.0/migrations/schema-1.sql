create table stored_file
(
    stored_file_id      bigserial       primary key,
    created_at          timestamp       default now(),
    created_by          varchar(255)    default 'system',
    modified_at         timestamp       default now(),
    modified_by         varchar(255)    default 'system',
    size_bytes          bigint          not null,
    owner               varchar(255)    not null,
    original_filename   varchar(255)    not null,
    version             bigint          default 0 not null
);

create table file_access_permission
(
    file_access_id      bigserial       primary key,
    created_at          timestamp       default now(),
    created_by          varchar(255)    default 'system',
    modified_at         timestamp       default now(),
    modified_by         varchar(255)    default 'system',
    user_name           varchar(255)    not null,
    permission_type     varchar(255)    not null,
    stored_file_id      bigint          not null
        constraint fk_file_access_stored_file references stored_file
);

