DROP TABLE held_in;
DROP TABLE belongs_to;
DROP TABLE tour;
DROP TABLE cities;
DROP TABLE genre;
DROP TABLE country;
DROP TABLE song;
DROP TABLE album;
DROP TABLE band;

CREATE TABLE band (
    band_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    CONSTRAINT band_name_key1 UNIQUE (name)
);

CREATE TABLE album (
    album_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    release_date DATE,
    total_duration INTEGER,
    band_ID INTEGER,
    FOREIGN KEY  (band_ID) REFERENCES band ON DELETE CASCADE
);

CREATE TABLE song (
    song_ID SERIAL NOT NULL PRIMARY KEY,
    title VARCHAR(30),
    duration INTEGER,
    band_ID  INTEGER,
    album_ID INTEGER,
    FOREIGN KEY  (album_ID) REFERENCES album ON DELETE CASCADE,
    FOREIGN KEY  (band_ID) REFERENCES band ON DELETE CASCADE
);


CREATE TABLE country (
    country_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    CONSTRAINT name_name_key1 UNIQUE (name)
);

CREATE TABLE genre (
    genre_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE cities (
    city_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    country_ID INTEGER,
    FOREIGN KEY  (country_ID) REFERENCES country
);

CREATE TABLE tour (
    tour_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    begin_date DATE,
    end_date DATE,
    band_ID INTEGER,
    FOREIGN KEY  (band_ID) REFERENCES band ON DELETE CASCADE
);

CREATE TABLE belongs_to (
    genre_ID INTEGER REFERENCES genre ON DELETE CASCADE,
    song_ID INTEGER REFERENCES song ON DELETE CASCADE,
    PRIMARY KEY    (genre_ID, song_ID)
);

CREATE TABLE held_in (
    tour_ID INTEGER REFERENCES tour(tour_ID) ON DELETE CASCADE,
    city_ID INTEGER REFERENCES cities(city_ID) ON DELETE CASCADE,
    PRIMARY KEY    (tour_ID, city_ID)
);




INSERT INTO band (name)
VALUES ('Rammstein');

INSERT INTO band (name)
VALUES ('Justin Bieber');

INSERT INTO band (name)
VALUES ('Alice Lou');

INSERT INTO band (name)
VALUES ('Rikky');

INSERT INTO album(name, release_date, total_duration, band_ID)
VALUES ('Carousel', '2009-10-01', 10221, (select band_ID from band where band.name='Rammstein'));

INSERT INTO album(name, release_date, total_duration, band_ID)
VALUES ('Pari', '2001-12-01', 13021, (select band_ID from band where band.name='Justin Bieber'));

INSERT INTO album(name, release_date, total_duration, band_ID)
VALUES ('Seven Donuts', '2008-12-01', 15021, (select band_ID from band where band.name='Alice Lou'));

INSERT INTO album(name, release_date, total_duration, band_ID)
VALUES ('Rain', '2007-12-01', 14321,  (select band_ID from band where band.name='Rikky'));



INSERT INTO song(title, duration, band_ID, album_ID)
VALUES ('Kiss me', 226, (select band_ID from band where band.name='Rammstein'), (select album_ID from album where album.name='Carousel'));

INSERT INTO song(title, duration, band_ID, album_ID)
VALUES ('Good Song', 232, (select band_ID from band where band.name='Justin Bieber'), (select album_ID from album where album.name='Pari'));

INSERT INTO song(title, duration, band_ID, album_ID)
VALUES ('Machen wir es Zusammen', 310, (select band_ID from band where band.name='Alice Lou'), (select album_ID from album where album.name='Seven Donuts'));

INSERT INTO song(title, duration, band_ID, album_ID)
VALUES ('Nichts mehr', 052, (select band_ID from band where band.name='Rikky'), (select album_ID from album where album.name='Rain'));



INSERT INTO country (name)
VALUES ('Russland');

INSERT INTO country (name)
VALUES ('Amerika');

INSERT INTO country (name)
VALUES ('Deutschland');

INSERT INTO country (name)
VALUES ('Frankreich');

INSERT INTO genre (name)
VALUES ('Indie');

INSERT INTO genre (name)
VALUES ('Pop');

INSERT INTO genre (name)
VALUES ('Rock');

INSERT INTO genre (name)
VALUES ('Metal');

INSERT INTO cities(name, country_ID)
VALUES ('Berlin', (select country_ID from country where country.name='Deutschland'));

INSERT INTO cities(name, country_ID)
VALUES ('München', (select country_ID from country where country.name='Deutschland'));

INSERT INTO cities(name, country_ID)
VALUES ('Moskau', (select country_ID from country where country.name='Russland'));

INSERT INTO cities(name, country_ID)
VALUES ('New York', (select country_ID from country where country.name='Amerika'));


INSERT INTO tour(name, begin_date, end_date, band_ID)
VALUES ('Tour Deutschland', '2001-12-01', '2002-12-01', (select band_ID from band where band.name='Rammstein'));

INSERT INTO tour(name, begin_date, end_date, band_ID)
VALUES ('Tour new2001', '2001-12-01', '2002-12-01', (select band_ID from band where band.name='Rammstein'));

INSERT INTO tour(name, begin_date, end_date, band_ID)
VALUES ('Expand 2003', '2001-12-01', '2003-12-01', (select band_ID from band where band.name='Alice Lou'));

INSERT INTO tour(name, begin_date, end_date, band_ID)
VALUES ('Jetzzt', '2001-12-01', '2004-12-01', (select band_ID from band where band.name='Rain'));


INSERT INTO belongs_to(genre_ID, song_ID)
VALUES ((select genre_ID from genre where genre.name='Indie'), (select song_ID from song where song.title='Nichts mehr'));

INSERT INTO belongs_to(genre_ID, song_ID)
VALUES ((select genre_ID from genre where genre.name='Rock'), (select song_ID from song where song.title='Nichts mehr'));

INSERT INTO belongs_to(genre_ID, song_ID)
VALUES ((select genre_ID from genre where genre.name='Pop'), (select song_ID from song where song.title='Kiss me'));

INSERT INTO belongs_to(genre_ID, song_ID)
VALUES ((select genre_ID from genre where genre.name='Indie'), (select song_ID from song where song.title='Good Song'));


INSERT INTO held_in(tour_ID,city_ID)
VALUES ((select tour_ID from tour where tour.name='Expand 2003'), (select city_ID from cities where cities.name='Berlin'));

INSERT INTO held_in(tour_ID,city_ID)
VALUES ((select tour_ID from tour where tour.name='Jetzzt'), (select city_ID from cities where cities.name='New York'));

INSERT INTO held_in(tour_ID,city_ID)
VALUES ((select tour_ID from tour where tour.name='Tour Deutschland'), (select city_ID from cities where cities.name='Berlin'));

INSERT INTO held_in(tour_ID,city_ID)
VALUES ((select tour_ID from tour where tour.name='Tour Deutschland'), (select city_ID from cities where cities.name='München'));

INSERT INTO held_in(tour_ID,city_ID)
VALUES ((select tour_ID from tour where tour.name='Tour new2001'), (select city_ID from cities where cities.name='Moskau'));