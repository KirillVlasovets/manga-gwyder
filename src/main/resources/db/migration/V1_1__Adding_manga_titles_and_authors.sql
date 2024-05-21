INSERT INTO authors (name, dob) VALUES
                                    ('Masashi Kishimoto', '1974-11-08'),       -- Автор Naruto
                                    ('Eiichiro Oda', '1975-01-01'),            -- Автор One Piece
                                    ('Tite Kubo', '1977-06-26'),               -- Автор Bleach
                                    ('Hajime Isayama', '1986-08-29'),          -- Автор Attack on Titan
                                    ('Tatsuki Fujimoto', '1992-10-10'),        -- Автор Chainsaw man
                                    ('Hiro Mashima', '1977-05-03'),            -- Автор Fairy Tale
                                    ('Kohei Horikoshi', '1986-11-20'),         -- Автор My hero academy
                                    ('Akira Toriyama', '1955-04-05'),          -- Автор Dragon Ball
                                    ('Yoshihiro Togashi', '1966-04-27'),       -- Автор Hunter x Hunter
                                    ('Rumiko Takahashi', '1957-10-10'),        -- Автор Inuyasha
                                    ('Kentaro Miura', '1966-07-11'),           -- Автор Berserk
                                    ('Naoko Takeuchi', '1967-03-15'),          -- Автор Sailor Moon
                                    ('Tsugumi Ohba', '1962-08-17');            -- Автор Death Note

INSERT INTO manga (title, description) VALUES
                                           ('Naruto', 'A young ninja who seeks recognition from his peers and dreams of becoming the Hokage, the village leader.'),
                                           ('One Piece', 'Follows the adventures of Monkey D. Luffy and his pirate crew in order to find the greatest treasure ever left by the legendary Pirate, Gold Roger.'),
                                           ('Bleach', 'Follows the adventures of Ichigo Kurosaki after he obtains the powers of a Soul Reaper.'),
                                           ('Attack on Titan', 'In a world where humanity lives inside enormous walled cities due to the sudden appearance of the Titans, gigantic humanoid creatures.'),
                                           ('Chainsaw Man', 'A story about Denji, a young man who has the ability to transform parts of his body into chainsaws.'),
                                           ('Fairy Tail', 'Follows the adventures of Natsu Dragneel and his guild, Fairy Tail.'),
                                           ('My Hero Academia', 'A world where people with superpowers, known as Quirks, are the norm, and follows a young boy without them.'),
                                           ('Dragon Ball', 'Follows the adventures of Goku from his childhood through adulthood as he trains in martial arts and explores the world.'),
                                           ('Hunter x Hunter', 'Follows a young boy named Gon Freecss, who discovers that his father, whom he was told was dead, is actually alive and a legendary Hunter.'),
                                           ('Inuyasha', 'A time-traveling high school girl and a young half-demon dog boy team up to retrieve the shards of a jewel of great power.'),
                                           ('Berserk', 'Follows Guts, a lone mercenary, as he wanders a dark world of medieval Europe inspired horror fantasy.'),
                                           ('Sailor Moon', 'A young girl named Usagi Tsukino transforms into the titular heroine and embarks on a mission to find the Moon Princess.'),
                                           ('Death Note', 'A high school student discovers a supernatural notebook that allows him to kill anyone whose name he writes in it.');

INSERT INTO manga_authors (manga_id, author_id) VALUES
                                                    ((SELECT id FROM manga WHERE title = 'Naruto'), (SELECT id FROM authors WHERE name = 'Masashi Kishimoto')),
                                                    ((SELECT id FROM manga WHERE title = 'One Piece'), (SELECT id FROM authors WHERE name = 'Eiichiro Oda')),
                                                    ((SELECT id FROM manga WHERE title = 'Bleach'), (SELECT id FROM authors WHERE name = 'Tite Kubo')),
                                                    ((SELECT id FROM manga WHERE title = 'Attack on Titan'), (SELECT id FROM authors WHERE name = 'Hajime Isayama')),
                                                    ((SELECT id FROM manga WHERE title = 'Chainsaw Man'), (SELECT id FROM authors WHERE name = 'Tatsuki Fujimoto')),
                                                    ((SELECT id FROM manga WHERE title = 'Fairy Tail'), (SELECT id FROM authors WHERE name = 'Hiro Mashima')),
                                                    ((SELECT id FROM manga WHERE title = 'My Hero Academia'), (SELECT id FROM authors WHERE name = 'Kohei Horikoshi')),
                                                    ((SELECT id FROM manga WHERE title = 'Dragon Ball'), (SELECT id FROM authors WHERE name = 'Akira Toriyama')),
                                                    ((SELECT id FROM manga WHERE title = 'Hunter x Hunter'), (SELECT id FROM authors WHERE name = 'Yoshihiro Togashi')),
                                                    ((SELECT id FROM manga WHERE title = 'Inuyasha'), (SELECT id FROM authors WHERE name = 'Rumiko Takahashi')),
                                                    ((SELECT id FROM manga WHERE title = 'Berserk'), (SELECT id FROM authors WHERE name = 'Kentaro Miura')),
                                                    ((SELECT id FROM manga WHERE title = 'Sailor Moon'), (SELECT id FROM authors WHERE name = 'Naoko Takeuchi')),
                                                    ((SELECT id FROM manga WHERE title = 'Death Note'), (SELECT id FROM authors WHERE name = 'Tsugumi Ohba'));
