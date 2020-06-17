create table users (
    id bigserial primary key,
    name varchar(255) not null,
    age int check (age > 0 and age < 200),
    created_at timestamp,
    updated_at timestamp
)