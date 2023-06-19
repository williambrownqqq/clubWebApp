create table faculty
(
    faculty_id  bigserial primary key,
    created_at  timestamp default now(),
    created_by  varchar(255) default 'system',
    modified_at timestamp default now(),
    modified_by varchar(255) default 'system',
    name        varchar(255)
);

create table users
(
    user_id      bigserial primary key,
    created_at   timestamp default now(),
    created_by   varchar(255) default 'system',
    modified_at  timestamp default now(),
    modified_by  varchar(255) default 'system',
    fathers_name varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    type         varchar(255) not null
);

create table cathedra
(
    cathedra_id bigserial primary key,
    created_at  timestamp default now(),
    created_by  varchar(255) default 'system',
    modified_at timestamp default now(),
    modified_by varchar(255) default 'system',
    name        varchar(255),
    faculty_id  bigint not null
        constraint fk_cathedra_faculty references faculty
);

create table user_cathedra
(
    user_id     bigint not null
        constraint fk_user_cathedra_users references users,
    cathedra_id bigint not null
        constraint fk_user_cathedra_cathedra references cathedra,
    primary key (user_id, cathedra_id)
);

create table account
(
    account_id             bigserial primary key,
    created_at             timestamp default now(),
    created_by             varchar(255)  default 'system',
    modified_at            timestamp default now(),
    modified_by            varchar(255) default 'system',
    active                 boolean default true not null,
    email                  varchar(255)         not null,
    email_activation_state integer default 0    not null,
    password               varchar(255)         not null,
    username               varchar(255)         not null
        constraint uk_account_username unique,
    user_id                bigint
        constraint uk_account_user_id unique
        constraint fk_account_users references users
);

create table role
(
    role_id     bigserial primary key,
    created_at  timestamp default now(),
    created_by  varchar(255) default 'system',
    modified_at timestamp default now(),
    modified_by varchar(255) default 'system',
    name        varchar(255)
);

create table account_role
(
    account_id bigint not null
        constraint fk_account_role_account references account,
    role_id    bigint not null
        constraint fk_account_role_role references role,
    primary key (account_id, role_id)
);
