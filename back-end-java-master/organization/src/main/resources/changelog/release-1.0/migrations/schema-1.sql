create table organization (
    organization_id     bigserial           primary key,
    created_at          timestamp           default now(),
    created_by          varchar(255)        default 'system',
    modified_at         timestamp           default now(),
    modified_by         varchar(255)        default 'system',
    head_id             bigint,
    name                varchar(255)        not null,
    owner_id            bigint
        constraint fk_organization_owner references organization
);

create table organization_participants (
    organization_id     bigserial           not null
        constraint fk_organization_participants_organization references organization,
    participant_id      bigint              not null
);

