ALTER TABLE chapters DROP CONSTRAINT fkManga;
ALTER TABLE chapters RENAME COLUMN mangaId TO manga_id;
ALTER TABLE chapters ADD CONSTRAINT fkManga FOREIGN KEY (manga_id) REFERENCES manga(id);
