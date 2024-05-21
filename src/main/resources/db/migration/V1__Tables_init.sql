create table if not exists manga
(
    id serial primary key,
    title text not null,
    description text not null
);

create table if not exists authors
(
    id serial primary key,
    name text not null,
    dob timestamp not null check ( dob > '1880-01-01' )
);

create table if not exists manga_authors
(
    manga_id integer references manga(id) not null,
    author_id integer references authors(id) not null
);
