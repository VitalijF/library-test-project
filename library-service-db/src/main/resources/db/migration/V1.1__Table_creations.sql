-- User
CREATE TABLE public.user
(
    id                    serial            NOT NULL PRIMARY KEY,
    first_name            character varying NOT NULL,
    last_name             character varying NOT NULL,
    membership_start_date DATE              NOT NULL,
    membership_until      DATE,
    gender                character         NOT NULL,
    UNIQUE (first_name, last_name)
);
ALTER TABLE public.user OWNER TO ${flyway:user};

-- Author
CREATE TABLE public.author
(
    id   serial            NOT NULL PRIMARY KEY,
    name character varying NOT NULL UNIQUE
);
ALTER TABLE public.author OWNER TO ${flyway:user};


-- Publisher
CREATE TABLE public.publisher
(
    id   serial           NOT NULL PRIMARY KEY,
    name character varying NOT NULL UNIQUE
);
ALTER TABLE public.publisher OWNER TO ${flyway:user};

-- Genre
CREATE TABLE public.genre
(
    id   serial           NOT NULL PRIMARY KEY,
    name character varying NOT NULL UNIQUE
);
ALTER TABLE public.genre OWNER TO ${flyway:user};


--Book
CREATE TABLE public.book
(
    id           SERIAL PRIMARY KEY,
    name         character varying NOT NULL UNIQUE,
    author_id    INTEGER REFERENCES public.author (id),
    genre_id     INTEGER REFERENCES public.genre (id),
    publisher_id INTEGER REFERENCES public.publisher (id)
);

ALTER TABLE public.book OWNER TO ${flyway:user};


--Borrowed
CREATE TABLE public.borrowed
(
    id       SERIAL PRIMARY KEY,
    book_id  INTEGER REFERENCES public.book (id),
    user_id  INTEGER REFERENCES public.user (id),
    start_at DATE NOT NULL,
    end_at   DATE,
    unique (book_id, user_id)
);
ALTER TABLE public.borrowed OWNER TO ${flyway:user};