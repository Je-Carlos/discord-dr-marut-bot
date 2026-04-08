create table if not exists app_metadata (
    metadata_key varchar(100) primary key,
    metadata_value varchar(500) not null,
    created_at timestamp with time zone not null default current_timestamp
);
