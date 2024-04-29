create table if not exists "customer"
(
    id        bigint generated by default as identity not null,
    created   bigint                                  not null,
    updated   bigint                                  not null,
    full_name varchar(255)                            not null,
    email     varchar(255)                            not null,
    phone     varchar(255)                            null,
    is_active boolean                                 not null,
    unique (email)
)