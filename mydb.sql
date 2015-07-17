DROP TABLE album;
DROP TABLE band;
DROP TABLE cities;
DROP TABLE Consist_of;
DROP TABLE country;
DROP TABLE song;
DROP TABLE tour;
DROP TABLE belongs_to;
DROP TABLE held_in;
DROP TABLE includes;
DROP TABLE genre;

CREATE TABLE band (
    band_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE album (
    album_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    release_date DATE,
    total_duration INTEGER
    -- REFERENCES song ON DELETE CASCADE
);

CREATE TABLE song (
    song_ID SERIAL NOT NULL PRIMARY KEY,
    title VARCHAR(30),
    duration INTEGER,
    band_ID  INTEGER,
    album_ID INTEGER,
     -- REFERENCES artists ON DELETE CASCADE
    FOREIGN KEY  (album_ID) REFERENCES album,
    FOREIGN KEY  (band_ID) REFERENCES band
);


CREATE TABLE country (
    country_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE genre (
    genre_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE cities (
    city_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    country_ID INTEGER,
     -- REFERENCES country ON DELETE CASCADE,
    FOREIGN KEY  (country_ID) REFERENCES country

);

CREATE TABLE tour (
    tour_ID SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    begin_date DATE,
    end_date DATE,
    band_ID INTEGER,
     -- REFERENCES band ON DELETE CASCADE
    FOREIGN KEY  (band_ID) REFERENCES band
);

CREATE TABLE belongs_to (
    genre_ID INTEGER REFERENCES genre ON DELETE CASCADE,
    song_ID INTEGER REFERENCES song ON DELETE CASCADE,
    -- FOREIGN KEY  (genre_ID) REFERENCES genre
    -- FOREIGN KEY  (song_ID) REFERENCES song
    PRIMARY KEY    (genre_ID, song_ID)
);

CREATE TABLE held_in (
    tour_ID INTEGER REFERENCES tour(tour_ID) ON DELETE CASCADE,
    city_ID INTEGER REFERENCES cities(city_ID) ON DELETE CASCADE,
    -- FOREIGN KEY  (tour_ID) REFERENCES tour
    -- FOREIGN KEY  (city_ID) REFERENCES cities
    PRIMARY KEY    (tour_ID, city_ID)
);