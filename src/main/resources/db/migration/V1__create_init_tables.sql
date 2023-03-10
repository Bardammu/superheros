CREATE TABLE authorities
(
    username  VARCHAR(255) NOT NULL,
    authority VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_authorities PRIMARY KEY (username)
);

CREATE TABLE superhero
(
    id        INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name      VARCHAR(50),
    gender    VARCHAR(50),
    origin    VARCHAR(50),
    birthdate date,
    CONSTRAINT pk_superhero PRIMARY KEY (id)
);

CREATE TABLE users
(
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (username)
);

ALTER TABLE superhero
    ADD CONSTRAINT uc_superhero_name UNIQUE (name);