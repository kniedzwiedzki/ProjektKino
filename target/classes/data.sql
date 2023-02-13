INSERT INTO AGE_RESTRICTIONS (MIN_AGE, NAME)
VALUES (0, 'G'),
       (0, 'PG'),
       (13, 'PG-13'),
       (17, 'R'),
       (17, 'NC-17');

INSERT INTO GENRES (name)
values ('Action'),
       ('Adventure'),
       ('Animation'),
       ('Comedy'),
       ('Crime'),
       ('Documentary'),
       ('Drama'),
       ('Family'),
       ('Fantasy'),
       ('History'),
       ('Horror'),
       ('Music'),
       ('Mystery'),
       ('Romance'),
       ('Science Fiction'),
       ('TV Movie'),
       ('Thriller'),
       ('War'),
       ('Western');

INSERT INTO MOVIES (TITLE, ORIGINAL_TITLE, RELEASE_YEAR, AGE_RESTRICTION_ID, DURATION, IMAGE_URL)
VALUES ('Gladiator', 'Gladiator', 2000, 5, 155, 'https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg'),
       ('Interstellar', 'Interstellar', 2014, 3, 160, 'https://fwcdn.pl/fpo/56/29/375629/7670122.3.jpg'),
       ('Incepcja', 'Inception', 2010, 3, 148, 'https://fwcdn.pl/fpo/08/91/500891/7354571.3.jpg'),
       ('Mroczny Rycerz', 'The Dark Knight', 2008, 3, 182, 'https://fwcdn.pl/fpo/63/51/236351/7198307.3.jpg'),
       ('Mroczny Rycerz Powstaje', 'The Dark Knight Rises', 2012, 3, 175,
        'https://fwcdn.pl/fpo/67/56/506756/7476576.3.jpg'),
       ('Prestiż', 'The Prestige', 2006, 2, 130, 'https://fwcdn.pl/fpo/99/45/259945/7536864.3.jpg'),
       ('Skazany na Shawshank', 'The Shawshank Redemption', 1994, 1, 142,
        'https://fwcdn.pl/fpo/10/48/1048/6925401.3.jpg'),
       ('Ojciec Chrzestny', 'The Godfather', 1972, 4, 185, 'https://fwcdn.pl/fpo/10/89/1089/7196615.3.jpg'),
       ('Ojciec Chrzestny II', 'The Godfather: Part II', 1974, 4, 181, 'https://fwcdn.pl/fpo/10/90/1090/7196616.3.jpg'),
       ('Ojciec Chrzestny III', 'The Godfather: Part III', 1990, 4, 163,
        'https://fwcdn.pl/fpo/10/91/1091/7196617.3.jpg'),
       ('Matrix', 'The Matrix', 1999, 3, 140, 'https://fwcdn.pl/fpo/06/28/628/7685907.3.jpg');


INSERT INTO MOVIES_X_GENRES (MOVIE_ID, GENRE_ID)
VALUES (1, 1),
       (1, 7),
       (1, 18),
       (2, 7),
       (2, 15),
       (3, 7),
       (3, 13),
       (4, 1),
       (4, 5),
       (4, 15),
       (5, 1),
       (5, 5),
       (5, 15),
       (6, 2),
       (6, 9),
       (6, 13),
       (6, 17),
       (7, 6),
       (7, 8),
       (8, 5),
       (8, 10),
       (9, 5),
       (9, 10),
       (10, 5),
       (10, 10),
       (11, 1),
       (11, 17),
       (11, 15);

INSERT INTO USERS (EMAIL, FIRSTNAME, LASTNAME, PASSWORD)
VALUES ('admin@kino.pl', 'Damian', 'Robicki', '$2a$10$3N5djNG34ceRhJcbYiPLHewRkuunAmYUiKkB8KuXI4VxO9eNUWmjq'),
       ('moderator@kino.pl', 'Jan', 'Kowalski', '$2a$10$AD07BI8UKv.uTLGa3s9e2OaTrTyr3WRyMS62nqZsLf7TcpRRDrady'),
       ('user1@kino.pl', 'Jan', 'Nowak', '$2a$10$O5quyDK5dW9yz83F5YbGyu4pe0HeERCw4a7nkYhjas2TmlZvWPSfq'),
       ('user2@kino.pl', 'Mario', 'Miras', '$2a$10$tXmmfMMY7NmakuuevTtyhe4dg9bNx7wqKcbIE638C9yWs/hQZC6LO');

INSERT INTO USER_X_ROLES (USER_ID, ROLE)
VALUES (1, 0),
       (1, 1),
       (1, 2),
       (2, 0),
       (2, 1),
       (3, 0),
       (4, 0);

