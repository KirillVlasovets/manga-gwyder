create table if not exists chapters
(
    id serial primary key,
    number integer not null,
    title text not null,
    mangaId integer not null,
    constraint fkManga foreign key(mangaId) references manga(id)
)
