create table process_instance
(
    process_instance_id bigserial primary key,
    created_at          timestamp,
    created_by          varchar(255),
    modified_at         timestamp,
    modified_by         varchar(255),
    schema_id           varchar(255),
    current_stage       bigint,
    is_finished         boolean default false,
    owner_id            bigint,
    owner_username      varchar(255),
    subordinate_id      bigint,
    stage_data          jsonb
);

create table process_job
(
    process_job_id   bigserial primary key,
    created_at       timestamp,
    created_by       varchar(255),
    modified_at      timestamp,
    modified_by      varchar(255),
    executed_on      timestamp,
    is_executed      boolean default false,
    owner_id         bigint,
    owner_username   varchar(255),
    participants_ids jsonb,
    schema_id        varchar(255),
    stage_data       jsonb,
    start_time       timestamp
);

create table document_review
(
    document_review_id  bigserial primary key,
    created_at          timestamp,
    created_by          varchar(255),
    modified_at         timestamp,
    modified_by         varchar(255),
    approves_received   bigint not null,
    approves_required   bigint not null,
    stage_id            bigint not null,
    status              varchar(255),
    text                text,
    uploader_id         bigint not null,
    process_instance_id bigint not null
        constraint document_review_process_instance references process_instance
);

create table document_review_attachments
(
    document_review_id bigint not null
        constraint document_review_attachments_document_review references document_review,
    attachment_id      bigint
);

create table document_review_reviewers
(
    document_review_id bigint not null
        constraint document_review_reviewers_document_review references document_review,
    reviewer_id        bigint
);

create table document_review_comment
(
    document_review_comment_id bigserial primary key,
    created_at                 timestamp,
    created_by                 varchar(255),
    modified_at                timestamp,
    modified_by                varchar(255),
    author_id                  bigint,
    text                       text   not null,
    document_review_id         bigint not null
        constraint document_review_comment_document_review references document_review
);

create table document_review_result
(
    document_review_result_id bigserial primary key,
    created_at                timestamp,
    created_by                varchar(255),
    modified_at               timestamp,
    modified_by               varchar(255),
    author_id                 bigint,
    is_approved               boolean,
    properties                jsonb,
    document_review_id        bigint not null
        constraint document_review_result_document_review references document_review
);


