create table template
(
    template_id         bigserial       primary key,
    created_at          timestamp       default now(),
    created_by          varchar(255)    default 'system',
    modified_at         timestamp       default now(),
    modified_by         varchar(255)    default 'system',
    name                varchar(1024)   not null unique,
    subject             varchar(1024)   not null,
    body                text            not null
);

create table email
(
    email_id            bigserial       primary key,
    created_at          timestamp       default now(),
    created_by          varchar(255)    default 'system',
    modified_at         timestamp       default now(),
    modified_by         varchar(255)    default 'system',
    subject             varchar(1024)   not null,
    sent_at             timestamp       not null,
    sent_by             varchar(255)    not null default 'system',
    sent_to             varchar(255)    not null
);

